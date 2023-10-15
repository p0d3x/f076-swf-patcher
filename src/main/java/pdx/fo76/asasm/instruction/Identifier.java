package pdx.fo76.asasm.instruction;

import pdx.fo76.asasm.SyntaxConstants;
import pdx.fo76.asasm.util.ParseUtil;

public interface Identifier {
    static Identifier parse(String str) {
        if (str == null) {
            return null;
        }
        var prefix = ParseUtil.callSiteName(str);
        return switch (prefix) {
            case SyntaxConstants.Q_NAME -> QName.parse(str);
            case SyntaxConstants.MULTINAME -> Multiname.parse(str);
            case SyntaxConstants.MULTINAME_L -> MultinameL.parse(str);
            case SyntaxConstants.TYPENAME -> TypeName.parse(str);
            case SyntaxConstants.RTQ_NAME -> RTQName.parse(str);
            default -> throw new IllegalArgumentException();
        };
    }
}
