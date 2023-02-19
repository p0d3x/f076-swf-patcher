package pdx.fo76.asasm.instruction;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class MultinameL implements Identifier {
    List<Namespace> namespaces;

    public String toString() {
        return "MultinameL(" + namespaces + ")";
    }

    public static MultinameL parse(String str) {
        var prefix = str.substring(0, str.indexOf("("));
        if (!prefix.equals("MultinameL")) {
            throw new IllegalArgumentException();
        }
        var nsbrackets = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")")).strip();
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
