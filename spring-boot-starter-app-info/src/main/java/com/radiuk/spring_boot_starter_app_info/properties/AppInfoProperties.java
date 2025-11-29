package com.radiuk.spring_boot_starter_app_info.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.info")
public record AppInfoProperties(
        boolean enabled,
        String projectName,
        String version
) {
}