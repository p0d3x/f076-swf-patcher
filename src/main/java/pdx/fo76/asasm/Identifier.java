package pdx.fo76.asasm;

public interface Identifier {
    static Identifier parse(String str) {
        var prefix = str.substring(0, str.indexOf("("));
        switch (prefix) {
            case "QName":
                return QName.parse(str);
            case "Multiname":
                return Multiname.parse(str);
            default:
                throw new IllegalArgumentException();
        }
    }
}
