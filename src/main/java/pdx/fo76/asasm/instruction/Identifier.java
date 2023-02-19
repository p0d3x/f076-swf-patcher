package pdx.fo76.asasm.instruction;

public interface Identifier {
    static Identifier parse(String str) {
        if (str == null) {
            return null;
        }
        var prefix = str.substring(0, str.indexOf("("));
        switch (prefix) {
            case "QName":
                return QName.parse(str);
            case "Multiname":
                return Multiname.parse(str);
            case "MultinameL":
                return MultinameL.parse(str);
            case "TypeName":
                return TypeName.parse(str);
            case "RTQName":
                return RTQName.parse(str);
            default:
                throw new IllegalArgumentException();
        }
    }
}
