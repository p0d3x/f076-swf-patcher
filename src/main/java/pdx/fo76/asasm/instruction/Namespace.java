package pdx.fo76.asasm.instruction;

import lombok.Value;
import pdx.fo76.asasm.SyntaxConstants;

import java.util.Arrays;
import java.util.stream.Collectors;

@Value
public class Namespace {
    String name;
    String[] scopes;

    public static Namespace ofPackage(String ... scopes) {
        return new Namespace(SyntaxConstants.PACKAGE_NAMESPACE, scopes);
    }

    public static Namespace ofPrivate(String ... scopes) {
        return new Namespace(SyntaxConstants.PRIVATE_NAMESPACE, scopes);
    }

    public static Namespace parse(String str) {
        var prefix = str.substring(0, str.indexOf("("));
        var args = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"));
        var scopes = args.split(",\\s*");
        for (int i = 0; i < scopes.length; i++) {
            scopes[i] = scopes[i].trim();
            if (scopes[i].startsWith("\"")) {
                scopes[i] = scopes[i].substring(1, scopes[i].length() - 1);
            } else if (scopes[i].equals("null")) {
                 scopes[i] = null;
            }
        }
        return new Namespace(prefix, scopes);
    }

    @Override
    public String toString() {
        var scopeStr = Arrays.stream(scopes)
                .map(s -> s == null ? null : "\"" + s + "\"")
                .collect(Collectors.joining(", "));
        return name + "(" + scopeStr + ")";
    }
}
