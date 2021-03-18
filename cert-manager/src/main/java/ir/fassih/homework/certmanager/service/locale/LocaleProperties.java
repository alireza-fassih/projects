package ir.fassih.homework.certmanager.service.locale;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cert-manager.locale")
public class LocaleProperties {
    private String propertiesName = "classpath*:*/*-locale-msg.properties";
    private String appTimeZone = "Asia/Tehran";
}
