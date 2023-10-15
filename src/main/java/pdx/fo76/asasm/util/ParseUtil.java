package pdx.fo76.asasm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ParseUtil {

    public static String stripParentheses(String str) {
        var start = str.indexOf("(") + 1;
        var end = str.lastIndexOf(")");
        return str.substring(start, end).strip();
    }

    public static String callSiteName(String str) {
        return str.substring(0, str.indexOf("("));
    }
}
