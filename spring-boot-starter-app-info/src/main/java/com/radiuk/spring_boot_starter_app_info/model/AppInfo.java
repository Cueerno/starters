package com.radiuk.spring_boot_starter_app_info.model;

import java.time.Instant;
import java.util.List;

public record AppInfo(
        String projectName,
        String version,
        Instant startedAt,
        List<String> activeProfiles
) {
}