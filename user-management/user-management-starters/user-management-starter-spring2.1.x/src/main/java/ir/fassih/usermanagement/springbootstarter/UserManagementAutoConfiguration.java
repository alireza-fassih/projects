package ir.fassih.usermanagement.springbootstarter;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan({"ir.fassih.usermanagement.dao.impl", "ir.fassih.usermanagement.business.requesthandler.impl"})
@EntityScan("ir.fassih.usermanagement.entities")
public class UserManagementAutoConfiguration {
}
