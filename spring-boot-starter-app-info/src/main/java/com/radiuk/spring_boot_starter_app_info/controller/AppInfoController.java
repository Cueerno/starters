package com.radiuk.spring_boot_starter_app_info.controller;

import com.radiuk.spring_boot_starter_app_info.properties.AppInfoProperties;
import com.radiuk.spring_boot_starter_app_info.model.AppInfo;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Arrays;

@RestController
public class AppInfoController {

    private final AppInfoProperties properties;
    private final Environment environment;
    private final Instant startedAt = Instant.now();

    public AppInfoController(AppInfoProperties properties, Environment environment) {
        this.properties = properties;
        this.environment = environment;
    }

    @GetMapping("/app/info")
    public AppInfo getAppInfo() {
        return new AppInfo(
                properties.projectName(),
                properties.version(),
                startedAt,
                Arrays.asList(environment.getActiveProfiles())
        );
    }
}