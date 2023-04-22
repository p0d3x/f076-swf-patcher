package pdx.fo76.asasm.instruction;

import pdx.fo76.asasm.SyntaxConstants;

public interface Identifier {
    static Identifier parse(String str) {
        if (str == null) {
            return null;
        }
        var prefix = str.substring(0, str.indexOf("("));
        switch (prefix) {
            case SyntaxConstants.Q_NAME:
                return QName.parse(str);
            case "Multiname":
                return Multiname.parse(str);
            case "MultinameL":
                return MultinameL.parse(str);
            case SyntaxConstants.TYPENAME:
                return TypeName.parse(str);
            case SyntaxConstants.RTQ_NAME:
                return RTQName.parse(str);
            default:
                throw new IllegalArgumentException();
        }
    }
}
