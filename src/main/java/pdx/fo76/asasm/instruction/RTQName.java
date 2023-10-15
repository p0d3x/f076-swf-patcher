package pdx.fo76.asasm.instruction;

import pdx.fo76.asasm.SyntaxConstants;
import pdx.fo76.asasm.util.ParseUtil;

public record RTQName(String name) implements Identifier {
    public String toString() {
        return "RTQName(" + (name == null ? null : "\"" + name + "\"") + ")";
    }

    public static RTQName parse(String str) {
        var prefix = ParseUtil.callSiteName(str);
        if (!SyntaxConstants.RTQ_NAME.equals(prefix)) {
            throw new IllegalArgumentException();
        }
        var name = ParseUtil.stripParentheses(str);
        if (name.equals("null")) {
            return new RTQName(null);
        }
        var nameNoQuotes = name.substring(1, name.length() - 1);

        return new RTQName(nameNoQuotes);
    }

}
