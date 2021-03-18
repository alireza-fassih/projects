package ir.fassih.workshopautomation.manager;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import ir.fassih.workshopautomation.core.datamanagment.model.OptionsModle;
import ir.fassih.workshopautomation.core.datamanagment.model.SearchModel;
import ir.fassih.workshopautomation.core.datamanagment.model.SearchModel.SearchType;
import ir.fassih.workshopautomation.entity.core.LogicallyDeletable;
import ir.fassih.workshopautomation.entity.core.Traceable;
import ir.fassih.workshopautomation.repository.AbstractRepository;
import ir.fassih.workshopautomation.rest.model.AbstractReportParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.internal.path.PluralAttributePath;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public abstract class AbstractManager<T, I extends Serializable> {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;


    protected final AbstractRepository<T, I> repository;
    protected final Class<T> className;

    @Transactional
    public void save(T entity) {
        beforeSave(entity);
        if (entity instanceof Traceable) {
            Traceable traceable = (Traceable) entity;
            if (traceable.getCreateDate() == null) {
                traceable.setCreateDate(new Date());
            }
            traceable.setLastModificationDate( new Date() );
        }
        repository.save(entity);
    }

    protected  void beforeSave( T entity ) {

    }

    @Transactional
    public Iterable<T> saveAll(Iterable<T> entities) {
        return repository.saveAll(entities);
    }

    @Transactional
    public void delete(I id) {
        T entity = find(id);
        if (entity instanceof LogicallyDeletable) {
            LogicallyDeletable logicallyDeletable = (LogicallyDeletable) entity;
            logicallyDeletable.setDeleted(Boolean.TRUE);
            save(entity);
        } else {
            repository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public Iterable<T> loadAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public T find(I id) {
        Optional<T> loaded = repository.findById(id);
        return loaded.isPresent() ? loaded.get() : null;
    }

    @Transactional
    public void update(I id, T entity) {
        T db =  find(id);
        BeanUtils.copyProperties(entity, db, ignoreFieldWhenUpdate());
        if (db instanceof Traceable) {
            Traceable traceable = (Traceable) db;
            traceable.setLastModificationDate(new Date());
        }
        save(db);
    }

    protected String[] ignoreFieldWhenUpdate() {
        return new String[]{};
    }


    @Transactional(readOnly = true)
    public Page<T> search(SearchModel model) {
        Specification<T> specification = createSpecification(model);
        return repository.findAll(specification, PageRequest.of(model.getPage() , model.getPageSize(), new Sort(Sort.Direction.DESC, "id")));
    }

    private Specification<T> createSpecification(SearchModel model) {
        return (root, query, builder) -> {
            query.distinct(true);
            return createPredicate(root, builder, model.getFilters());
        };
    }

    private Predicate createPredicate(Root<T> root, CriteriaBuilder builder, Map<String, String> filters) {
        List<Predicate> predicates = new ArrayList<>();
        filters.forEach((key, value) -> {

            String field = null;
            SearchType searchType = null;

            for (SearchModel.SearchType type : SearchModel.SearchType.values()) {
                String prefix = type.getPrefix();
                if (key.startsWith(prefix)) {
                    field = key.replace(prefix, "");
                    searchType = type;
                    break;
                }
            }

            if (searchType == null) {
                log.debug("ignore unknown search param {}", key);
                return;
            }

            Path<?> element = null;

            if (field.contains(".")) {
                for (String path : field.split("\\.")) {
                    element = (element == null ? root.get(path) : element.get(path));
                    if(element instanceof PluralAttributePath) {
                        element = root.join(path);
                    }
                }
            } else {
                element = root.get(field);
            }

            Object realVal = convertValue(element.getJavaType(), value);

            if (searchType == SearchType.EQ) {
                if(Boolean.class.isAssignableFrom(element.getJavaType()) && Boolean.FALSE.equals(realVal) ) {
                    predicates.add(builder.or(builder.equal(element, realVal), builder.isNull(element)));
                } else {
                    predicates.add(builder.equal(element, realVal));
                }
            } else if (searchType == SearchType.LIKE) {
                predicates.add(builder.like(( Path<String> )element, "%"+realVal.toString()+"%"));
            } else if ( searchType == SearchType.LE ) {
                if (realVal instanceof Comparable) {
                    Comparable val = (Comparable) realVal;
                    predicates.add(builder.lessThanOrEqualTo(root.get(field), val));
                } else {
                    log.warn("{} is not comparable ignore le opration", realVal.getClass());
                }
            } else if( searchType == SearchType.GE ) {
                if (realVal instanceof Comparable) {
                    Comparable val = (Comparable) realVal;
                    predicates.add(builder.greaterThanOrEqualTo(root.get(field), val));
                } else {
                    log.warn("{} is not comparable ignore ge opration", realVal.getClass());
                }
            }
        });
        return builder.and(predicates.toArray(new Predicate[]{}));
    }


    @Transactional(readOnly = true)
    public List<T> findAll(SearchModel model) {
        return repository.findAll(createSpecification(model));
    }

    @Transactional(readOnly = true)
    public List<T> loadNotDeletes() {
        return repository.findAll(
                (root, query, builder) ->
                        builder.or(builder.notEqual(root.get("deleted"), Boolean.TRUE), builder.isNull(root.get("deleted")))
        );
    }

    private Object convertValue(Class<?> javaType, String value) {
        if (Date.class.isAssignableFrom(javaType)) {
            try {
                return new StdDateFormat().parse( value );
            } catch (ParseException e) {
                return new Date(Long.parseLong(value));
            }
        } else if (Long.class.isAssignableFrom(javaType)) {
            return Long.parseLong(value);
        } else if (Boolean.class.isAssignableFrom(javaType)) {
            return Boolean.parseBoolean(value);
        }
        return value;
    }


    @Transactional(readOnly = true)
    public List<OptionsModle> loadOptions() {
        Stream<T> dataSteam = null;
        if (LogicallyDeletable.class.isAssignableFrom(className)) {
            dataSteam = loadNotDeletes().stream();
        } else {
            dataSteam = StreamSupport.stream(loadAll().spliterator(), false);
        }
        return dataSteam
                .map(this::convertToOptionsModle).collect(Collectors.toList());
    }

    protected OptionsModle convertToOptionsModle(T entity) {
        return modelMapper.map(entity, OptionsModle.class);
    }


    @Transactional
    public void restore(I id) {
        T entity = find(id);
        if (entity instanceof LogicallyDeletable) {
            LogicallyDeletable logicallyDeletable = (LogicallyDeletable) entity;
            logicallyDeletable.setDeleted(Boolean.FALSE);
            save(entity);
        }
    }

    @Transactional(readOnly = true)
    public <C> List<C> report(AbstractReportParam<C, T> param) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<C> query = cb.createQuery(param.getConstructType());
        Root< T > root = query.from( className );
        query.select( param.createConstructor( cb , root) )
            .where( createPredicate( root, cb, param.getFilters() ) )
            .groupBy( param.createGroupedBy( cb , root ) );
        return entityManager.createQuery(query).getResultList();
    }

}
