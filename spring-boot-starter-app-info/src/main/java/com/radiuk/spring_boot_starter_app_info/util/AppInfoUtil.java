package com.radiuk.spring_boot_starter_app_info.util;

public class AppInfoUtil {

    private AppInfoUtil(){}

    public static String formatUptime(long seconds) {
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        if (days > 0) {
            return String.format("%d days %02d:%02d:%02d", days, hours, minutes, secs);
        }
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    public static String formatBytes(long bytes) {
        double kb = bytes / 1024.0;
        double mb = kb / 1024.0;
        double gb = mb / 1024.0;

        if (gb >= 1) return String.format("%.2f GB", gb);
        if (mb >= 1) return String.format("%.2f MB", mb);
        if (kb >= 1) return String.format("%.2f KB", kb);

        return bytes + " B";
    }
}
