package pdx.fo76.asasm;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class QName implements Identifier {
    private final Namespace namespace;
    private final String fieldName;

    public static QName ofPackage(String className) {
        return of(Namespace.ofPackage(""), className);
    }

    public static QName parse(String str) {
        var prefix = str.substring(0, str.indexOf("("));
        if (!prefix.equals("QName")) {
            throw new IllegalArgumentException();
        }
        var args = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"));
        var ns = Namespace.parse(args.substring(0, args.lastIndexOf(")") + 1));
        var quoted = args.substring(args.lastIndexOf(",") + 1).trim();
        var fieldName = quoted.substring(1, quoted.length() - 1);
        return QName.of(ns, fieldName);
    }

    public String toString() {
        return "QName(" + namespace + ", \"" + fieldName + "\")";
    }

    public static QName of(Namespace namespace, String fieldName) {
        return new QName(namespace, fieldName);
    }

}
