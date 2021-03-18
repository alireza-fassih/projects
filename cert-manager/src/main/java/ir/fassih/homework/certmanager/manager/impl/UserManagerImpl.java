package ir.fassih.homework.certmanager.manager.impl;

import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import ir.fassih.homework.certmanager.entities.UserEntity;
import ir.fassih.homework.certmanager.manager.UserManager;
import ir.fassih.homework.certmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagerImpl implements UserManager {


    private final EntityManager  entityManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public void save(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    public List<UserEntity> search() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserEntity> searchByQuery(String query) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteria.from(UserEntity.class);

        if(StringUtils.hasText(query)) {
            RSQLVisitor<Predicate, EntityManager> visitor =
                    new JpaPredicateVisitor<UserEntity>().defineRoot(root);
            Node rootNode = new RSQLParser().parse(query);
            Predicate predicate = rootNode.accept(visitor, entityManager);
            criteria.where(predicate);
        }

        return entityManager.createQuery(criteria).getResultList();
    }

}
