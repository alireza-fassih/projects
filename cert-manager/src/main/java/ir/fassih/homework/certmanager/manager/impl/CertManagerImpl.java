package ir.fassih.homework.certmanager.manager.impl;

import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import ir.fassih.homework.certmanager.entities.CertEntity;
import ir.fassih.homework.certmanager.entities.UserEntity;
import ir.fassih.homework.certmanager.manager.CertManager;
import ir.fassih.homework.certmanager.repositories.CertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CertManagerImpl implements CertManager {


    private final EntityManager entityManager;
    private final CertRepository certRepository;

    @Override
    public void save(CertEntity entity) {
        certRepository.save(entity);
    }

    @Override
    public List<CertEntity> searchByQuery(String query) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CertEntity> criteria = builder.createQuery(CertEntity.class);
        Root<CertEntity> root = criteria.from(CertEntity.class);

        if(StringUtils.hasText(query)) {
            RSQLVisitor<Predicate, EntityManager> visitor =
                    new JpaPredicateVisitor<UserEntity>().defineRoot(root);
            Node rootNode = new RSQLParser().parse(query);
            Predicate predicate = rootNode.accept(visitor, entityManager);
            criteria.where(predicate);
        }

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public void delete(long id) {
        certRepository.deleteById(id);
    }

    @Override
    public CertEntity load(long id) {
        return certRepository.findById(id).get();
    }

    @Override
    public CertEntity loadByCode(String code) {
        return certRepository.findByCode(code);
    }

}