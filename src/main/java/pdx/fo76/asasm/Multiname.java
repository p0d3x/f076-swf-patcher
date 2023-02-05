package pdx.fo76.asasm;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class Multiname implements Identifier {
    String fieldName;
    List<Namespace> namespaces = new ArrayList<>();

    public String toString() {
        return "Multiname(\"" + fieldName + "\", " + namespaces + ")";
    }

    public static Multiname parse(String str) {
        var prefix = str.substring(0, str.indexOf("("));
        if (!prefix.equals("Multiname")) {
            throw new IllegalArgumentException();
        }
        var args = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")")).split(",", 2);
        var quoted = args[0].strip();
        var fieldName = quoted.substring(1, quoted.length() - 1);

        var nsbrackets = args[1].strip();
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

        return Multiname.of(fieldName, namespaces);
    }

    public static Multiname of(String fieldName, Namespace ... namespaces) {
        return of(fieldName, List.of(namespaces));
    }

    public static Multiname of(String fieldName, List<Namespace> namespaces) {
        var mn = new Multiname(fieldName);
        mn.getNamespaces().addAll(namespaces);
        return mn;
    }

}
