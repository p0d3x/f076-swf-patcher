package pdx.fo76.asasm.instruction;

import pdx.fo76.asasm.SyntaxConstants;
import pdx.fo76.asasm.util.ParseUtil;

import java.util.ArrayList;
import java.util.List;

public record MultinameL(List<Namespace> namespaces) implements Identifier {
    public String toString() {
        return SyntaxConstants.MULTINAME_L + "(" + namespaces + ")";
    }

    public static MultinameL parse(String str) {
        var prefix = ParseUtil.callSiteName(str);
        if (!SyntaxConstants.MULTINAME_L.equals(prefix)) {
            throw new IllegalArgumentException();
        }
        var nsbrackets = ParseUtil.stripParentheses(str);
        var ns = nsbrackets.substring(1, nsbrackets.length() - 1);
        var nss = ns.strip();
        var namespaces = new ArrayList<Namespace>();
        while (!nss.isEmpty()) {
            var nextnss = nss.substring(0, nss.indexOf(")") + 1);
            var nextns = Namespace.parse(nextnss);
            namespaces.add(nextns);
            nss = nss.substring(nextnss.length()).strip();
            if (!nss.isBlank()) {
                nss = nss.substring(1).strip();
            }
        }
        return new MultinameL(namespaces);
    }

}
