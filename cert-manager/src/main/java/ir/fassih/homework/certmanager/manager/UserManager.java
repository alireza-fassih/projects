package ir.fassih.homework.certmanager.manager;

import ir.fassih.homework.certmanager.entities.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserManager {

    @Transactional
    void save(UserEntity userEntity);

    @Transactional(readOnly = true)
    List<UserEntity> search();

    @Transactional
    void delete(Long id);

    @Transactional(readOnly = true)
    List<UserEntity> searchByQuery(String query);

}

