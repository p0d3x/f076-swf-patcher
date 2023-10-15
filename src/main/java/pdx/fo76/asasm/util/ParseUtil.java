package pdx.fo76.asasm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ParseUtil {

    public static String callSiteName(String str) {
        return extractPrefix(str, "(");
    }

    public static String extractPrefix(String args, String delimiter) {
        return args.substring(0, args.indexOf(delimiter));
    }

    public static String stripEnclosed(String args, String startEnclosure, String endEnclosure) {
        var start = args.indexOf(startEnclosure) + 1;
        var end = args.lastIndexOf(endEnclosure);
        return args.substring(start, end).strip();
    }

    public static String stripParentheses(String str) {
        return stripEnclosed(str, "(", ")");
    }
}
