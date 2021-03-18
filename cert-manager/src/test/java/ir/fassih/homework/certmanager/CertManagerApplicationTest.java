package ir.fassih.homework.certmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class CertManagerApplicationTest {

    @Autowired
    private ApplicationContext context;
    @Test
    void contextLoads() {
        Assertions.assertNotNull( context );
    }

}