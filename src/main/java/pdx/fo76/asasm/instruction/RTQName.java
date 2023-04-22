package pdx.fo76.asasm.instruction;

import lombok.Value;
import pdx.fo76.asasm.SyntaxConstants;

@Value
public class RTQName implements Identifier {
    String name;

    public String toString() {
        return "RTQName(" + (name == null ? null : "\"" + name + "\"") + ")";
    }

    public static RTQName parse(String str) {
        var prefix = str.substring(0, str.indexOf("("));
        if (!prefix.equals(SyntaxConstants.RTQ_NAME)) {
            throw new IllegalArgumentException();
        }
        var name = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")")).strip();
        if (name.equals("null")) {
            return new RTQName(null);
        }
        var nameNoQuotes = name.substring(1, name.length() - 1);

        return new RTQName(nameNoQuotes);
    }

}
