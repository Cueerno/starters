package com.radiuk.spring_boot_starter_app_info.auto_config;

import com.radiuk.spring_boot_starter_app_info.properties.AppInfoProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(AppInfoProperties.class)
@ConditionalOnProperty(value = "app.info.enabled", havingValue = "true", matchIfMissing = true)
public class AppInfoAutoConfiguration {
}