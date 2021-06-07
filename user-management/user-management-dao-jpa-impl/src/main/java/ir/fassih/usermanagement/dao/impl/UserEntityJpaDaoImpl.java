package ir.fassih.usermanagement.dao.impl;

import ir.fassih.usermanagement.dao.IUserEntityDao;
import ir.fassih.usermanagement.entities.UserEntity;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Named
public class UserEntityJpaDaoImpl implements IUserEntityDao {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<UserEntity> findById(Long id) {
        UserEntity userEntity = entityManager.find(UserEntity.class, id);
        return Optional.ofNullable(userEntity);
    }

    @Override
    public boolean save(UserEntity entity) {
        entityManager.persist(entity);
        return true;
    }

    @Override
    public boolean update(UserEntity entity) {
        entityManager.merge(entity);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        entityManager.remove(findById(id));
        return true;
    }
}
