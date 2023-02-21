package lims.api.util;

public class ThreadUtil {

    public static String getCurrentMethodName() {
        return getStackTrace(3);
    }

    public static String getCallerMethodName() {
        return getStackTrace(4);
    }

    private static String getStackTrace(int depth) {
        return Thread.currentThread().getStackTrace()[depth].getMethodName();
    }

}