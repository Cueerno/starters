package com.radiuk.spring_boot_starter_app_info.model;

import java.time.Instant;
import java.util.List;

public record AppInfo(

        String projectName,
        String version,
        Instant startedAt,
        String uptimeFormatted,
        long uptimeSeconds,
        List<String> activeProfiles,

        String javaVersion,
        String osName,
        int cpuCores,
        String jvmUsedMemoryFormatted,
        String jvmMaxMemoryFormatted,
        double systemCpuLoad,
        double systemLoadAverage,

        List<GCInfo> garbageCollectors,

        int threadCount,
        int daemonThreadCount,

        String springBootVersion,
        int beansCount
) {}