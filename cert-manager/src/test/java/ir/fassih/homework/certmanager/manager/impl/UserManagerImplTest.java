package ir.fassih.homework.certmanager.manager.impl;

import ir.fassih.homework.certmanager.entities.UserEntity;
import ir.fassih.homework.certmanager.manager.UserManager;
import ir.fassih.homework.certmanager.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Transactional
@SpringBootTest
class UserManagerImplTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserManager userManager;



    @Test
    public void testSearchByQuery() {
        UserEntity user1 = createUser("user1");
        userRepository.save(user1);


        UserEntity user2 = createUser("user2");
        userRepository.save(user2);


        List<UserEntity> userEntities =
                userManager.searchByQuery("username==user1");

        assertEquals(1, userEntities.size());
        UserEntity result = userEntities.get(0);

        assertEquals(user1.getId(), result.getId());
    }

    private UserEntity createUser(String username) {
        UserEntity user1 = new UserEntity();
        user1.setUsername(username);
        user1.setPassword("some password");
        return user1;
    }
}