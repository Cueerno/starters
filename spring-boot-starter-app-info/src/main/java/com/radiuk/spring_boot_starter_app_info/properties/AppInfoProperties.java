package com.radiuk.spring_boot_starter_app_info.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.info")
public class AppInfoProperties {
    private boolean enabled = true;
    private String projectName = "Unknown";
    private String version = "0.0.1";
}
