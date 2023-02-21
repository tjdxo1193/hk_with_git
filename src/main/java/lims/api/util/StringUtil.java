package lims.api.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {

    private static final Pattern base64Pattern = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");

    public static String camelToSnake(String str) {
        String ret = str.replaceAll("([A-Z])", "_$1").replaceAll("([a-z][A-Z])", "$1_$2");
        return ret.toLowerCase();
    }

    public static String snakeToCamel(String str) {
        String[] fragments = str.toLowerCase().split("_");
        return fragments[0] + Arrays.stream(Arrays.copyOfRange(fragments, 1, fragments.length))
                .map(s -> Strings.isEmpty(s) ? s : s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining());
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateUUID(int length) {
        String uuid = generateUUID();
        return uuid.replaceAll("-", "").substring(0, length);
    }

    public static boolean isEncodedBase64(String s) {
        return base64Pattern.matcher(s).find();
    }

    public static String encodeUTF8(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    public static String decodeUTF8(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }

    public static String getOrDefault(String value, String defaultValue) {
        return StringUtils.isNotEmpty(value) ? value : defaultValue;
    }

    public static String substr(String s, int limit) {
        if (StringUtils.isNotEmpty(s) && s.length() > limit) {
            return s.substring(0, limit);
        }
        return s;
    }

    public static class Date {

        public static String addHyphen(String date) {
            if (date == null) {
                return null;
            }
            return date.replaceAll("(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");
        }
    }
}