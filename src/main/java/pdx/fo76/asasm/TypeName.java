package pdx.fo76.asasm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypeName implements Identifier {
    private final QName type;
    private final Identifier param;

    @Override
    public String toString() {
        return "TypeName(" + type + "<" + param + ">)";
    }

    public static Identifier parse(String str) {
        var prefix = str.substring(0, str.indexOf("("));
        if (prefix.equals("QName")) {
            return QName.parse(str);
        } else if (!prefix.equals("TypeName")) {
            throw new IllegalArgumentException();
        }
        var args = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"));
        var qns = args.substring(0, args.indexOf("<"));
        var param = args.substring(args.indexOf("<") + 1, args.lastIndexOf(">"));
        var qn = QName.parse(qns);
        var qn2 = TypeName.parse(param);
        return new TypeName(qn, qn2);
    }
}
