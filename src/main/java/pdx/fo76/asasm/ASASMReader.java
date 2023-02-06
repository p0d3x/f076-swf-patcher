package pdx.fo76.asasm;

import lombok.extern.slf4j.Slf4j;
import pdx.fo76.asasm.instruction.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ASASMReader {

    private ASASMReader() {}

    public static Node readNodeFromTemplate(Path sourcePath, String className) throws IOException {
        try (var stream = Files.lines(sourcePath, StandardCharsets.UTF_8)) {
            var lines = stream.map(l -> l.replace("<className>", className)).toList();
            return readTree(lines);
        }
    }

    public static Node readTree(List<String> lines) {
        return readTree(lines, new RootNode());
    }

    private static Node readTree(List<String> lines, Node parent) {
        for (int i = 0; i < lines.size();) {
            var lineClean = lines.get(i).strip();
            var isEnd = lineClean.startsWith("end");
            if (isEnd) {
                parent.add(new End(lineClean.substring(4)));
                break;
            }
            String line = lines.get(i);
            if (line.isBlank()) {
                parent.add(new BlankLine());
                i++;
            } else if (line.startsWith("L")) {
                parent.add(new LineLabel(lineClean));
                i++;
            } else {
                var parts = lineClean.split(" ", 2);
                var linesRemaining = lines.subList(i + 1, lines.size());
                i += readChildren(linesRemaining, parent, parts);
            }
        }
        return parent;
    }

    private static int readChildren(List<String> lines, Node result, String[] parts) {
        var next = readNext(lines, parts);
        result.add(next);
        return next.length();
    }

    private static Node readNext(List<String> lines, String[] parts) {
        var keyword = parts[0];
        var args = parts.length == 2 ? parts[1].strip() : "";
        return switch (keyword) {
            case "class" -> readTree(lines, new ASClass());
            case "refid" -> new RefId(readStringLiteral(args));
            case "extends" -> new Extends(QName.parse(readIdentifier(args)));
            case "protectedns" -> new ProtectedNS(Namespace.parse(args));
            case "flag" -> new Flag(args);
            case "trait" -> readTrait(lines, args);
            case "instance" -> readTree(lines, new Instance(QName.parse(readIdentifier(args))));
            case "iinit" -> readTree(lines, new Iinit());
            case "name" -> new Name(readStringLiteral(args));
            case "body" -> readTree(lines, new Body());
            case "code" -> readTree(lines, new Code());
            case "dup" -> new Dup();
            case "newactivation" -> new NewActivation();
            case "pushscope" -> new PushScope();
            case "debugfile" -> new DebugFile(readStringLiteral(args));
            case "debugline" -> new DebugLine(Integer.parseInt(args));
            case "debug" -> readDebug(args);
            case "maxstack" -> new MaxStack(Integer.parseInt(args));
            case "localcount" -> new LocalCount(Integer.parseInt(args));
            case "initscopedepth" -> new InitScopeDepth(Integer.parseInt(args));
            case "maxscopedepth" -> new MaxScopeDepth(Integer.parseInt(args));
            case "method" -> readTree(lines, new Method());
            case "cinit" -> readTree(lines, readNotImplemented(keyword, args));
            case "callpropvoid" -> readCallPropVoid(args);
            case "constructprop" -> readConstructProp(args);
            case "findpropstrict" -> new FindPropStrict(Identifier.parse(readIdentifier(args)));
            case "getproperty" -> new GetProperty(Identifier.parse(readIdentifier(args)));
            case "setproperty" -> new SetProperty(Identifier.parse(readIdentifier(args)));
            case "getlex" -> new GetLex(Identifier.parse(readIdentifier(args)));
            case "getscopeobject" -> new GetScopeObject(Integer.parseInt(args));
            case "getlocal0" -> new GetLocal0();
            case "getlocal1" -> new GetLocal1();
            case "getlocal2" -> new GetLocal2();
            case "getlocal3" -> new GetLocal3();
            case "getlocal" -> new GetLocal(Integer.parseInt(args));
            case "setlocal1" -> new SetLocal1();
            case "setlocal2" -> new SetLocal2();
            case "setlocal3" -> new SetLocal3();
            case "setlocal" -> new SetLocal(Integer.parseInt(args));
            case "getslot" -> new GetSlot(Integer.parseInt(args));
            case "setslot" -> new SetSlot(Integer.parseInt(args));
            case "initproperty" -> new InitProperty(Identifier.parse(readIdentifier(args)));
            case "applytype" -> new ApplyType(Integer.parseInt(args));
            case "pushstring" -> new PushString(readStringLiteral(args));
            case "pushbyte" -> new PushByte(Integer.parseInt(args));
            case "pushnull" -> new PushNull();
            case "pushfalse" -> new PushFalse();
            case "pushtrue" -> new PushTrue();
            case "construct" -> new Construct(Integer.parseInt(args));
            case "constructsuper" -> new ConstructSuper(Integer.parseInt(args));
            case "coerce" -> new Coerce(Identifier.parse(args));
            case "coerce_a" -> new CoerceA();
            case "newfunction" -> new NewFunction(readStringLiteral(args));
            case "returnvoid" -> new ReturnVoid();
            case "param" -> new Param(Identifier.parse(readIdentifier(args)));
            case "paramname" -> new ParamName(readStringLiteral(args));
            case "returns" -> new Returns(Identifier.parse(readIdentifier(args)));
            case "returnvalue" -> new ReturnValue();
            case "convert_b" -> new ConvertB();
            case "convert_d" -> new ConvertD();
            case "convert_u" -> new ConvertU();
            case "iftrue" -> new IfTrue(new LineLabel(args));
            case "iffalse" -> new IfFalse(new LineLabel(args));
            case "ifeq" -> new IfEq(new LineLabel(args));
            case "ifne" -> new IfNe(new LineLabel(args));
            case "ifstrictne" -> new IfStrictNe(new LineLabel(args));
            case "ifnge" -> new IfNge(new LineLabel(args));
            case "ifngt" -> new IfNgt(new LineLabel(args));
            case "ifnlt" -> new IfNlt(new LineLabel(args));
            case "iflt" -> new IfLt(new LineLabel(args));
            case "jump" -> new Jump(new LineLabel(args));
            case "callproperty" -> readCallProperty(args);
            case "equals" -> new Equals();
            case "divide" -> new Divide();
            case "multiply" -> new Multiply();
            case "subtract" -> new Subtract();
            case "add" -> new Add();
            case "pop" -> new Pop();
            case "not" -> new Not();
            case "label" -> new Label();
            case "increment" -> new Increment();
            case "decrement" -> new Decrement();
            case "optional" -> new OptionalParam(args);
            case "kill" -> new Kill(Integer.parseInt(args));
            case ";" -> new Comment(args);
            default -> readNotImplemented(keyword, parts.length == 2 ? parts[1] : "");
        };
    }

    private static CallProperty readCallProperty(String arg) {
        var qNameStr = readIdentifier(arg);
        var arg2Str = arg.substring(qNameStr.length() + 1).strip();
        var qName = Identifier.parse(qNameStr);
        return new CallProperty(qName, Integer.parseInt(arg2Str));
    }

    private static Debug readDebug(String arg) {
        var parts = arg.split(",");
        var arg1 = Integer.parseInt(parts[0].strip());
        var arg2 = readStringLiteral(parts[1].strip());
        var arg3 = Integer.parseInt(parts[2].strip());
        var arg4 = Integer.parseInt(parts[3].strip());
        return new Debug(arg1, arg2, arg3, arg4);
    }

    private static String readStringLiteral(String arg) {
        if (arg.equals("null")) {
            return null;
        }
        return arg.substring(1, arg.length() - 1);
    }

    private static NotImplemented readNotImplemented(String keyword, String args) {
        return new NotImplemented(keyword, args);
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

    private static Node readTrait(List<String> lines, String args) {
        var rest = args.split(" ", 2);
        return switch (rest[0]) {
            case "getter" -> readTree(lines, parseTraitGetter(args));
            case "setter" -> readTree(lines, parseTraitSetter(args));
            case "method" -> readTree(lines, parseTraitMethod(args));
            case "slot" -> parseTraitSlot(args);
            case "const" -> parseTraitConst(args);
            default -> new NotImplemented("trait", args);
        };
    }

    public static TraitGetter parseTraitGetter(String part) {
        if (part.isBlank()) {
            throw new IllegalArgumentException();
        }
        QName qName = null;
        List<Flag> flags = new ArrayList<>();
        parseOpts:
        while (!part.isBlank()) {
            var parts = part.strip().split(" ", 2);
            switch (parts[0]) {
                case "getter":
                    String qNameStr = readIdentifier(parts[1]);
                    qName = QName.parse(qNameStr);
                    if (qNameStr.length() == parts[1].length()) {
                        break parseOpts;
                    }
                    part = parts[1].substring(qNameStr.length());
                    break;
                case "flag":
                    parts = parts[1].split(" ", 2);
                    flags.add(new Flag(parts[0]));
                    if (parts.length == 1) {
                        break parseOpts;
                    }
                    part = parts[1];
                    break;
                case "end":
                    break parseOpts;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return new TraitGetter(qName, flags);
    }


    public static TraitSetter parseTraitSetter(String part) {
        if (part.isBlank()) {
            throw new IllegalArgumentException();
        }
        QName qName = null;
        List<Flag> flags = new ArrayList<>();
        parseOpts:
        while (!part.isBlank()) {
            var parts = part.strip().split(" ", 2);
            switch (parts[0]) {
                case "setter":
                    String qNameStr = readIdentifier(parts[1]);
                    qName = QName.parse(qNameStr);
                    if (qNameStr.length() == parts[1].length()) {
                        break parseOpts;
                    }
                    part = parts[1].substring(qNameStr.length());
                    break;
                case "flag":
                    parts = parts[1].split(" ", 2);
                    flags.add(new Flag(parts[0]));
                    if (parts.length == 1) {
                        break parseOpts;
                    }
                    part = parts[1];
                    break;
                case "end":
                    break parseOpts;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return new TraitSetter(qName, flags);
    }

    public static TraitMethod parseTraitMethod(String part) {
        if (part.isBlank()) {
            throw new IllegalArgumentException();
        }
        QName qName = null;
        List<Flag> flags = new ArrayList<>();
        parseOpts:
        while (!part.isBlank()) {
            var parts = part.strip().split(" ", 2);
            switch (parts[0]) {
                case "method":
                    String qNameStr = readIdentifier(parts[1]);
                    qName = QName.parse(qNameStr);
                    if (qNameStr.length() == parts[1].length()) {
                        break parseOpts;
                    }
                    part = parts[1].substring(qNameStr.length());
                    break;
                case "flag":
                    parts = parts[1].split(" ", 2);
                    flags.add(new Flag(parts[0]));
                    if (parts.length == 1) {
                        break parseOpts;
                    }
                    part = parts[1];
                    break;
                case "end":
                    break parseOpts;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return new TraitMethod(qName, flags);
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
                    type = Identifier.parse(typeName);
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

    public static TraitConst parseTraitConst(String part) {
        if (part.isBlank()) {
            throw new IllegalArgumentException();
        }
        var parts = part.split(" ", 2);
        Identifier qName = null;
        Integer slotId = null;
        Identifier type = null;
        String initValue = null;
        parseOpts:
        while (!part.isBlank()) {
            switch (parts[0]) {
                case "const":
                    String qNameStr = readIdentifier(parts[1]);
                    qName = Identifier.parse(qNameStr);
                    part = parts[1].substring(qNameStr.length() + 1);
                    break;
                case "slotid":
                    var strVal = parts[1].substring(0, parts[1].indexOf(" "));
                    slotId = Integer.valueOf(strVal);
                    part = parts[1].substring(strVal.length() + 1);
                    break;
                case "type":
                    String typeName = readTypeName(parts[1]);
                    type = Identifier.parse(typeName);
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
        return new TraitConst(qName, slotId, type, initValue);
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
        if (str.equals("null")) {
            return null;
        }
        int o = 0;
        int end = -1;
        if (str.indexOf('(') == -1) {
            throw new IllegalArgumentException();
        }
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
