package pdx.fo76.asasm.instruction;

import lombok.RequiredArgsConstructor;
import pdx.fo76.asasm.SyntaxConstants;
import pdx.fo76.asasm.util.ParseUtil;

@RequiredArgsConstructor
public class TypeName implements Identifier {
    private final QName type;
    private final Identifier param;

    @Override
    public String toString() {
        return "TypeName(" + type + "<" + param + ">)";
    }

    public static Identifier parse(String str) {
        var prefix = ParseUtil.callSiteName(str);
        if (!SyntaxConstants.TYPENAME.equals(prefix)) {
            throw new IllegalArgumentException();
        }
        var args = ParseUtil.stripParentheses(str);
        var qns = ParseUtil.extractPrefix(args, "<");
        var param = ParseUtil.stripEnclosed(args, "<", ">");
        var qn = QName.parse(qns);
        var qn2 = Identifier.parse(param);
        return new TypeName(qn, qn2);
    }
}
