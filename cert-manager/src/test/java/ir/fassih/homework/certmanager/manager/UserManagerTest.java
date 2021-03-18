package ir.fassih.homework.certmanager.manager;

import ir.fassih.homework.certmanager.entities.UserEntity;
import ir.fassih.homework.certmanager.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class UserManagerTest {

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void encryptPasswordBeforeSavingUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("username");
        userEntity.setPassword("some password");

        userManager.save(userEntity);

        Optional<UserEntity> userOpt = userRepository.findByUsername("username");
        assertTrue(userOpt.isPresent());

        UserEntity user = userOpt.get();
        assertNotEquals("some password", user.getPassword());
    }
}