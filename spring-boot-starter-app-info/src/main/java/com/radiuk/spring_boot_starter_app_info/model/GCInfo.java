package com.radiuk.spring_boot_starter_app_info.model;

public record GCInfo(
        String name,
        long collectionCount,
        long collectionTime
) {}
