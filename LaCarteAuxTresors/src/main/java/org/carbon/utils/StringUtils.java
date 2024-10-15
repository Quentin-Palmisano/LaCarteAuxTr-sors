package org.carbon.utils;

public class StringUtils {
    public static String removeWhitespace(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\s", "");
    }

    public static boolean isComment(String str) {
        return str.startsWith("#");
    }

    public static boolean isPlayerLine(String str) {
        return str.startsWith("A");
    }

    public static boolean isMapLine(String str) {
        return str.startsWith("C");
    }

    public static boolean isMountainLine(String str) {
        return str.startsWith("M");
    }

    public static boolean isTreasureLine(String str) {
        return str.startsWith("T");
    }

    public static boolean isAcceptedLine(String str) {
        return isComment(str) || isPlayerLine(str) || isMapLine(str) || isMountainLine(str) || isTreasureLine(str);
    }
}
