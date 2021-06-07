package ir.fassih.workshopautomation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@EntityScan("ir.fassih.workshopautomation")
public class WorkshopAutomationApplication {

    @Value("${app.timezone}")
    private String timeZone;

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(WorkshopAutomationApplication.class, args);
        for( String s : run.getBeanDefinitionNames() ) {
            log.info("bean name {}", s);
        }
    }
}
