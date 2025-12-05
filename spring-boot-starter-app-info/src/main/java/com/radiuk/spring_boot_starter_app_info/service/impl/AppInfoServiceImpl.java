package com.radiuk.spring_boot_starter_app_info.service.impl;

import com.radiuk.spring_boot_starter_app_info.model.AppInfo;
import com.radiuk.spring_boot_starter_app_info.model.GCInfo;
import com.radiuk.spring_boot_starter_app_info.model.WebServerInfo;
import com.radiuk.spring_boot_starter_app_info.properties.AppInfoProperties;
import com.radiuk.spring_boot_starter_app_info.service.AppInfoService;
import com.radiuk.spring_boot_starter_app_info.util.AppInfoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.web.server.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.boot.web.server.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class AppInfoServiceImpl implements AppInfoService {

    private final Instant startedAt = Instant.now();

    private final AppInfoProperties properties;
    private final Environment environment;
    private final ApplicationContext applicationContext;


    @Override
    public AppInfo getAppInfo() {
        Runtime runtime = Runtime.getRuntime();

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        long uptimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
        long uptimeSeconds = uptimeMs / 1000;

        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        List<GCInfo> gcInfoList = ManagementFactory.getGarbageCollectorMXBeans()
                .stream()
                .map(gc -> new GCInfo(
                        gc.getName(),
                        gc.getCollectionCount(),
                        gc.getCollectionTime()
                ))
                .toList();

        return new AppInfo(
                properties.getProjectName(),
                properties.getVersion(),
                startedAt,
                AppInfoUtil.formatUptime(uptimeSeconds),
                uptimeSeconds,
                Arrays.asList(environment.getActiveProfiles()),

                System.getProperty("java.version"),
                System.getProperty("os.name"),
                runtime.availableProcessors(),
                AppInfoUtil.formatBytes(runtime.totalMemory() - runtime.freeMemory()),
                AppInfoUtil.formatBytes(runtime.maxMemory()),
                osBean.getAvailableProcessors(),
                osBean.getSystemLoadAverage(),

                gcInfoList,

                threadBean.getThreadCount(),
                threadBean.getDaemonThreadCount(),

                SpringBootVersion.getVersion(),
                applicationContext.getBeanDefinitionCount(),

                collectWebServerInfo()
        );
    }
    private WebServerInfo collectWebServerInfo() {
        String serverType = "unknown";
        Integer port = null;
        Boolean httpsEnabled = null;
        String connectionTimeout = environment.getProperty("server.connection-timeout");
        String sessionTimeout = environment.getProperty("server.servlet.session.timeout");

        if (applicationContext instanceof ServletWebServerApplicationContext servletCtx) {
            var webServer = servletCtx.getWebServer();
            if (webServer != null) {
                String impl = webServer.getClass().getName().toLowerCase();

                if (impl.contains("tomcat")) serverType = "tomcat";
                else if (impl.contains("jetty")) serverType = "jetty";
                else if (impl.contains("undertow")) serverType = "undertow";
                else serverType = webServer.getClass().getSimpleName();

                try {
                    port = webServer.getPort();
                } catch (Throwable ignored) {}
            }
        }

        if (applicationContext instanceof ReactiveWebServerApplicationContext reactiveCtx) {
            var webServer = reactiveCtx.getWebServer();
            if (webServer != null) {
                String impl = webServer.getClass().getName().toLowerCase();

                if (impl.contains("netty")) serverType = "netty";
                else serverType = webServer.getClass().getSimpleName();

                try {
                    port = webServer.getPort();
                } catch (Throwable ignored) {}
            }
        }

        if (port == null) {
            port = tryParseInt(environment.getProperty("local.server.port"));
            if (port == null) port = tryParseInt(environment.getProperty("server.port"));
        }

        String sslEnabledProp = environment.getProperty("server.ssl.enabled");
        if (sslEnabledProp != null) {
            httpsEnabled = Boolean.parseBoolean(sslEnabledProp);
        } else if (environment.getProperty("server.ssl.key-store") != null) {
            httpsEnabled = true;
        }

        return new WebServerInfo(
                serverType,
                port,
                httpsEnabled,
                connectionTimeout,
                sessionTimeout
        );
    }

    private Integer tryParseInt(String val) {
        if (val == null) return null;
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return null;
        }
    }
}
