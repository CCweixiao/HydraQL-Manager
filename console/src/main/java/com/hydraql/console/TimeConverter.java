package com.hydraql.console;


/**
 * @author leojie 2023/7/29 23:31
 */
public class TimeConverter {
    public static String humanReadableCost(long end, long start) {
        if (end <= start) {
            return humanReadableCost(0);
        }
        return humanReadableCost(end - start);
    }

    public static String humanReadableCost(long ms) {
        long interval = ms / 1000;
        if (interval < 1) {
            return ms + "ms";
        }
        long modMs = ms % 1000;
        StringBuilder sb = new StringBuilder();
        int days = (int) (interval / 86400L);
        int hours = (int) (interval - (86400L * days)) / 3600;
        int minutes = (int) (interval - (86400L * days) - (long) (3600 * hours)) / 60;
        int seconds = (int) (interval - (86400L * days) - (long) (3600 * hours) - (long) (60 * minutes));
        if (days > 0) {
            sb.append(days);
            sb.append("d,");
        }

        if (hours > 0) {
            sb.append(hours);
            sb.append("h,");
        }

        if (minutes > 0) {
            sb.append(minutes);
            sb.append("m,");
        }

        if (seconds > 0) {
            sb.append(seconds);
            sb.append("s,");
        }

        if (modMs > 0) {
            sb.append(modMs);
            sb.append("ms");
        }
        return sb.toString();

    }

    public static void main(String[] args) {
        System.out.println(TimeConverter.humanReadableCost(1213133));
    }
}
