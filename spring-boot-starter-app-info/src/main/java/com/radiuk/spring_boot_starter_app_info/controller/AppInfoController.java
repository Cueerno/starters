package com.radiuk.spring_boot_starter_app_info.controller;

import com.radiuk.spring_boot_starter_app_info.model.AppInfo;
import com.radiuk.spring_boot_starter_app_info.service.AppInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppInfoController {

    private final AppInfoService appInfoService;

    @GetMapping("/app/info")
    public ResponseEntity<AppInfo> getAppInfo() {
        return ResponseEntity.ok(appInfoService.getAppInfo());
    }
}