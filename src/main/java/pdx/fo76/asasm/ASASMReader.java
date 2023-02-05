package pdx.fo76.asasm;

import lombok.extern.slf4j.Slf4j;
import pdx.fo76.asasm.instruction.BlankLine;
import pdx.fo76.asasm.instruction.CallPropVoid;
import pdx.fo76.asasm.instruction.ConstructProp;
import pdx.fo76.asasm.instruction.End;
import pdx.fo76.asasm.instruction.FindPropStrict;
import pdx.fo76.asasm.instruction.GetLocal0;
import pdx.fo76.asasm.instruction.InitProperty;
import pdx.fo76.asasm.instruction.Label;
import pdx.fo76.asasm.instruction.Node;
import pdx.fo76.asasm.instruction.NotImplemented;
import pdx.fo76.asasm.instruction.PushString;
import pdx.fo76.asasm.instruction.TraitSlot;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class ASASMReader {

    private ASASMReader() {}

    public static Node readNodeFromTemplate(Path sourcePath, String className) throws IOException {
        try (var stream = Files.lines(sourcePath, StandardCharsets.UTF_8)) {
            var lines = stream.map(l -> l.replace("<className>", className)).toList();
            return readNode(lines);
        }
    }

    public static Node readNode(List<String> lines) {
        var first = lines.get(0);
        int indent = 0;
        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) == ' ') {
                indent++;
            } else {
                break;
            }
        }
        return readNode(indent, lines, new Node());
    }

    private static Node readNode(int indent, List<String> lines, Node result) {
        for (int i = 0; i < lines.size();) {
            var lineClean = lines.get(i).strip();
            var isEnd = lineClean.startsWith("end");
            if (isEnd) {
                result.add(new End(lineClean.substring(4)));
                break;
            }
            String line = lines.get(i);
            if (line.isBlank()) {
                result.add(new BlankLine());
                i++;
            } else if (line.startsWith("L")) {
                result.add(new Label(lineClean));
                i++;
            } else {
                var parts = line.substring(indent).split(" ", 2);
                i += readChildren(indent, lines, result, i, parts);
            }
        }
        return result;
    }

    private static int readChildren(int indent, List<String> lines, Node result, int i, String[] parts) {
        var next = readNext(indent, lines, i, parts);
        result.add(next);
        return next.length();
    }

    private static Node readNext(int indent, List<String> lines, int i, String[] parts) {
        return switch (parts[0]) {
            case "trait" -> readTrait(indent, lines, i, parts);
            case "instance", "class", "method", "iinit", "cinit", "body", "code" ->
                    readNode(indent + 1, lines.subList(i + 1, lines.size()), new NotImplemented(parts[0], parts.length >= 2 ? parts[1] : null));
            case "callpropvoid" -> readCallPropVoid(parts[1].strip());
            case "constructprop" -> readConstructProp(parts[1].strip());
            case "findpropstrict" -> new FindPropStrict(Identifier.parse(readIdentifier(parts[1].strip())));
            case "getlocal0" -> new GetLocal0();
            case "initproperty" -> new InitProperty(Identifier.parse(readIdentifier(parts[1].strip())));
            case "pushstring" -> new PushString(parts[1].strip().substring(1, parts[1].strip().length() - 1));
            default -> new NotImplemented(parts[0], parts.length >= 2 ? parts[1] : null);
        };
    }

    private static Node readTrait(int indent, List<String> lines, int i, String[] parts) {
        var rest = parts[1].split(" ", 2);
        return switch (rest[0]) {
            case "getter", "setter", "method" ->
                    readNode(indent + 1, lines.subList(i + 1, lines.size()), new NotImplemented(parts[0], parts[1]));
            case "slot" -> parseTraitSlot(parts[1]);
            default -> new NotImplemented(parts[0], parts[1]);
        };
    }

    private static Node readCallPropVoid(String args) {
        var method = Identifier.parse(readIdentifier(args));
        var numArgs = Integer.parseInt(args.substring(args.lastIndexOf(",") + 1).strip());
        return new CallPropVoid(method, numArgs);
    }

    private static Node readConstructProp(String args) {
        var clazz = Identifier.parse(readIdentifier(args));
        var numArgs = Integer.parseInt(args.substring(args.lastIndexOf(",") + 1).strip());
        return new ConstructProp(clazz, numArgs);
    }

    public static TraitSlot parseTraitSlot(String part) {
        if (part.isBlank()) {
            throw new IllegalArgumentException();
        }
        var parts = part.split(" ", 2);
        Identifier slot = null;
        Integer slotId = null;
        Identifier type = null;
        String initValue = null;
        parseOpts:
        while (!part.isBlank()) {
            switch (parts[0]) {
                case "slot":
                    String qName = readIdentifier(parts[1]);
                    slot = Identifier.parse(qName);
                    part = parts[1].substring(qName.length() + 1);
                    break;
                case "slotid":
                    var strVal = parts[1].substring(0, parts[1].indexOf(" "));
                    slotId = Integer.valueOf(strVal);
                    part = parts[1].substring(strVal.length() + 1);
                    break;
                case "type":
                    String typeName = readTypeName(parts[1]);
                    type = TypeName.parse(typeName);
                    part = parts[1].substring(typeName.length() + 1);
                    break;
                case "value":
                    initValue = parts[1].substring(0, parts[1].indexOf(")") + 1);
                    part = parts[1].substring(initValue.length() + 1);
                    break;
                case "end":
                    break parseOpts;
                default:
                    throw new IllegalArgumentException();
            }
            parts = part.trim().split(" ", 2);
        }
        return new TraitSlot(slot, slotId, type, initValue);
    }

    private static String readTypeName(String str) {
        var mName = str.substring(0, str.indexOf("("));
        if (mName.equals("TypeName")) {
            return str.substring(0, str.lastIndexOf(">") + 2);
        } else if (mName.equals("QName")) {
            return readIdentifier(str);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static String readIdentifier(String str) {
        int o = 0;
        int end = -1;
        for (int i = str.indexOf('('); i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                o++;
            } else if (str.charAt(i) == ')') {
                o--;
            }
            if (o == 0) {
                end = i;
                break;
            }
        }
        if (end == -1) {
            throw new IllegalArgumentException();
        }
        return str.substring(0, end + 1);
    }
}
