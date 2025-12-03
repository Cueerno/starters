package com.radiuk.spring_boot_starter_app_info.service.impl;

import com.radiuk.spring_boot_starter_app_info.model.AppInfo;
import com.radiuk.spring_boot_starter_app_info.properties.AppInfoProperties;
import com.radiuk.spring_boot_starter_app_info.service.AppInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.util.Arrays;

@RequiredArgsConstructor
public class AppInfoServiceImpl implements AppInfoService {

    private final AppInfoProperties properties;
    private final Environment environment;
    private final Instant startedAt = Instant.now();

    @Override
    public AppInfo getAppInfo() {
        long uptimeSeconds = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;

        return new AppInfo(
                properties.getProjectName(),
                properties.getVersion(),
                startedAt,
                Arrays.asList(environment.getActiveProfiles()),
                System.getProperty("java.version"),
                System.getProperty("os.name") + " " + System.getProperty("os.version"),
                uptimeSeconds
        );
    }
}
