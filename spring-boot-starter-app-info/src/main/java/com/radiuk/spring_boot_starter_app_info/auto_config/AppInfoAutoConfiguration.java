package com.radiuk.spring_boot_starter_app_info.auto_config;

import com.radiuk.spring_boot_starter_app_info.controller.AppInfoController;
import com.radiuk.spring_boot_starter_app_info.properties.AppInfoProperties;
import com.radiuk.spring_boot_starter_app_info.service.AppInfoService;
import com.radiuk.spring_boot_starter_app_info.service.impl.AppInfoServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@AutoConfiguration
@EnableConfigurationProperties(AppInfoProperties.class)
@ConditionalOnProperty(value = "app.info.enabled", havingValue = "true", matchIfMissing = true)
public class AppInfoAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AppInfoService appInfoService(AppInfoProperties appInfoProperties, Environment environment, ApplicationContext context) {
        return new AppInfoServiceImpl(appInfoProperties, environment, context);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public AppInfoController appInfoController(AppInfoService appInfoService) {
        return new AppInfoController(appInfoService);
    }
}