package com.radiuk.spring_boot_starter_app_info.service.impl;

import com.radiuk.spring_boot_starter_app_info.model.AppInfo;
import com.radiuk.spring_boot_starter_app_info.properties.AppInfoProperties;
import com.radiuk.spring_boot_starter_app_info.service.AppInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AppInfoServiceImpl implements AppInfoService {

    private final AppInfoProperties properties;
    private final Environment environment;
    private final Instant startedAt = Instant.now();

    @Override
    public AppInfo getAppInfo() {
        return new AppInfo(
                properties.projectName(),
                properties.version(),
                startedAt,
                Arrays.asList(environment.getActiveProfiles())
        );
    }
}
