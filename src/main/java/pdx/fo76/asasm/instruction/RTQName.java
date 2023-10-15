package pdx.fo76.asasm.instruction;

import pdx.fo76.asasm.SyntaxConstants;
import pdx.fo76.asasm.util.ParseUtil;

public record RTQName(String name) implements Identifier {
    public String toString() {
        return SyntaxConstants.RTQ_NAME + "(" + (name == null ? null : "\"" + name + "\"") + ")";
    }

    public static RTQName parse(String str) {
        var prefix = ParseUtil.callSiteName(str);
        if (!SyntaxConstants.RTQ_NAME.equals(prefix)) {
            throw new IllegalArgumentException();
        }
        var name = ParseUtil.stripParentheses(str);
        if (SyntaxConstants.NULL_LITERAL.equals(name)) {
            return new RTQName(null);
        }
        var nameNoQuotes = name.substring(1, name.length() - 1);

        return new RTQName(nameNoQuotes);
    }

}
