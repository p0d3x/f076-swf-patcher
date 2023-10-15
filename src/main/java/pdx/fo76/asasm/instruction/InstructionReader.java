package pdx.fo76.asasm.instruction;

import lombok.extern.slf4j.Slf4j;
import pdx.fo76.asasm.util.ParseUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static pdx.fo76.asasm.SyntaxConstants.*;

@Slf4j
public class InstructionReader {

    public static Node readNodeFromTemplate(Path sourcePath, String className) throws IOException {
        try (var stream = Files.lines(sourcePath, StandardCharsets.UTF_8)) {
            var lines = stream.map(l -> l.replace("<className>", className)).toList();
            return readTree(lines);
        }
    }

    public static Node readTree(List<String> lines) {
        return readTree(new RootNode(), lines);
    }

    private static Node readTree(Node root, List<String> lines) {
        for (int i = 0; i < lines.size();) {
            var lineClean = lines.get(i).strip();
            var isEnd = lineClean.startsWith(END);
            if (isEnd) {
                root.add(new End(lineClean.substring(4)));
                break;
            }
            if (lineClean.isBlank()) {
                root.add(new BlankLine());
                i++;
            } else if (lineClean.startsWith("L")) {
                root.add(new LineLabel(lineClean));
                i++;
            } else {
                var parts = lineClean.split(" ", 2);
                var next = readNext(lines.subList(i + 1, lines.size()), parts);
                root.add(next);
                i += next.length();
            }
        }
        return root;
    }

    private static Node readNext(List<String> lines, String[] parts) {
        var token = parts[0];
        var args = parts.length == 2 ? parts[1].strip() : "";
        return switch (token) {
            case CLASS -> readTree(new ASClass(), lines);
            case REF_ID -> new RefId(readStringLiteral(args));
            case EXTENDS -> new Extends(QName.parse(readIdentifier(args)));
            case PROTECTED_NS -> new ProtectedNS(Namespace.parse(args));
            case FLAG -> new Flag(args);
            case TRAIT -> readTrait(lines, args);
            case INSTANCE -> readTree(new Instance(QName.parse(readIdentifier(args))), lines);
            case IINIT -> readTree(new Iinit(), lines);
            case NAME -> new Name(readStringLiteral(args));
            case BODY -> readTree(new Body(), lines);
            case CODE -> readTree(new Code(), lines);
            case "dup" -> new Dup();
            case "newactivation" -> new NewActivation();
            case PUSH_SCOPE -> new PushScope();
            case DEBUG_FILE -> new DebugFile(readStringLiteral(args));
            case DEBUG_LINE -> new DebugLine(Integer.parseInt(args));
            case DEBUG -> readDebug(args);
            case MAX_STACK -> new MaxStack(Integer.parseInt(args));
            case LOCAL_COUNT -> new LocalCount(Integer.parseInt(args));
            case INIT_SCOPE_DEPTH -> new InitScopeDepth(Integer.parseInt(args));
            case MAX_SCOPE_DEPTH -> new MaxScopeDepth(Integer.parseInt(args));
            case METHOD -> readTree(new Method(), lines);
            case CINIT -> readTree(readNotImplemented(token, args), lines);
            case "callpropvoid" -> readCallPropVoid(args);
            case "constructprop" -> readConstructProp(args);
            case FIND_PROP_STRICT -> new FindPropStrict(Identifier.parse(readIdentifier(args)));
            case "getproperty" -> new GetProperty(Identifier.parse(readIdentifier(args)));
            case "setproperty" -> new SetProperty(Identifier.parse(readIdentifier(args)));
            case "getlex" -> new GetLex(Identifier.parse(readIdentifier(args)));
            case "getscopeobject" -> new GetScopeObject(Integer.parseInt(args));
            case GET_LOCAL_0 -> new GetLocal0();
            case GET_LOCAL_1 -> new GetLocal1();
            case GET_LOCAL_2 -> new GetLocal2();
            case GET_LOCAL_3 -> new GetLocal3();
            case "getlocal" -> new GetLocal(Integer.parseInt(args));
            case "setlocal1" -> new SetLocal1();
            case "setlocal2" -> new SetLocal2();
            case "setlocal3" -> new SetLocal3();
            case "setlocal" -> new SetLocal(Integer.parseInt(args));
            case "getslot" -> new GetSlot(Integer.parseInt(args));
            case "setslot" -> new SetSlot(Integer.parseInt(args));
            case "initproperty" -> new InitProperty(Identifier.parse(readIdentifier(args)));
            case "applytype" -> new ApplyType(Integer.parseInt(args));
            case PUSH_STRING -> new PushString(readStringLiteral(args));
            case PUSH_BYTE -> new PushByte(Integer.parseInt(args));
            case PUSH_INT -> new PushInt(Integer.parseInt(args));
            case PUSH_SHORT -> new PushShort(Integer.parseInt(args));
            case PUSH_NULL -> new PushNull();
            case PUSH_FALSE -> new PushFalse();
            case PUSH_TRUE -> new PushTrue();
            case PUSH_DOUBLE -> new PushDouble(args);
            case "construct" -> new Construct(Integer.parseInt(args));
            case "constructsuper" -> new ConstructSuper(Integer.parseInt(args));
            case "coerce" -> new Coerce(Identifier.parse(args));
            case "coerce_a" -> new CoerceA();
            case "newfunction" -> new NewFunction(readStringLiteral(args));
            case "newobject" -> new NewObject(Integer.parseInt(args));
            case "newcatch" -> new NewCatch(Integer.parseInt(args));
            case "callsupervoid" -> new CallSuperVoid(args);
            case "hasnext2" -> new HasNext2(args);
            case "newarray" -> new NewArray(Integer.parseInt(args));
            case FIND_PROPERTY -> new FindProperty(args);
            case "lookupswitch" -> new LookupSwitch(args);
            case RETURN_VOID -> new ReturnVoid();
            case "param" -> new Param(Identifier.parse(readIdentifier(args)));
            case "paramname" -> new ParamName(readStringLiteral(args));
            case "returns" -> new Returns(Identifier.parse(readIdentifier(args)));
            case RETURN_VALUE -> new ReturnValue();
            case "convert_b" -> new ConvertB();
            case "convert_d" -> new ConvertD();
            case "convert_u" -> new ConvertU();
            case "iftrue" -> new IfTrue(new LineLabel(args));
            case "iffalse" -> new IfFalse(new LineLabel(args));
            case "ifeq" -> new IfEq(new LineLabel(args));
            case "ifne" -> new IfNe(new LineLabel(args));
            case "ifgt" -> new IfGt(new LineLabel(args));
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
            default -> readNotImplemented(token, args);
        };
    }

    private static String readTypeName(String str) {
        var mName = ParseUtil.callSiteName(str);
        if (TYPENAME.equals(mName)) {
            return str.substring(0, str.lastIndexOf(">") + 2);
        } else if (Q_NAME.equals(mName)) {
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

    private static StringLiteral readStringLiteral(String arg) {
        return new StringLiteral(arg.equals("null") ? null : arg.substring(1, arg.length() - 1));
    }

    private static NotImplemented readNotImplemented(String keyword, String args) {
        return new NotImplemented(keyword, args);
    }

    private static Debug readDebug(String arg) {
        var parts = arg.split(",");
        var arg1 = Integer.parseInt(parts[0].strip());
        var arg2 = readStringLiteral(parts[1].strip());
        var arg3 = Integer.parseInt(parts[2].strip());
        var arg4 = Integer.parseInt(parts[3].strip());
        return new Debug(arg1, arg2, arg3, arg4);
    }

    private static CallProperty readCallProperty(String args) {
        var qName = Identifier.parse(readIdentifier(args));
        var numArgs = Integer.parseInt(args.substring(args.lastIndexOf(",") + 1).strip());
        return new CallProperty(qName, numArgs);
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
            case "getter" -> readTree(parseTraitGetter(args), lines);
            case "setter" -> readTree(parseTraitSetter(args), lines);
            case METHOD -> readTree(parseTraitMethod(args), lines);
            case "slot" -> parseTraitSlot(args);
            case "const" -> parseTraitConst(args);
            default -> new NotImplemented(TRAIT, args);
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
                case FLAG:
                    parts = parts[1].split(" ", 2);
                    flags.add(new Flag(parts[0]));
                    if (parts.length == 1) {
                        break parseOpts;
                    }
                    part = parts[1];
                    break;
                case END:
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
                case FLAG:
                    parts = parts[1].split(" ", 2);
                    flags.add(new Flag(parts[0]));
                    if (parts.length == 1) {
                        break parseOpts;
                    }
                    part = parts[1];
                    break;
                case END:
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
                case METHOD:
                    String qNameStr = readIdentifier(parts[1]);
                    qName = QName.parse(qNameStr);
                    if (qNameStr.length() == parts[1].length()) {
                        break parseOpts;
                    }
                    part = parts[1].substring(qNameStr.length());
                    break;
                case FLAG:
                    parts = parts[1].split(" ", 2);
                    flags.add(new Flag(parts[0]));
                    if (parts.length == 1) {
                        break parseOpts;
                    }
                    part = parts[1];
                    break;
                case END:
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
        Slot slot = null;
        SlotId slotId = null;
        Type type = null;
        Value initValue = null;
        parseOpts:
        while (!part.isBlank()) {
            switch (parts[0]) {
                case "slot":
                    var qName = readIdentifier(parts[1]);
                    slot = new Slot(Identifier.parse(qName));
                    part = parts[1].substring(qName.length() + 1);
                    break;
                case SLOT_ID:
                    var strVal = parts[1].substring(0, parts[1].indexOf(" "));
                    slotId = new SlotId(Integer.valueOf(strVal));
                    part = parts[1].substring(strVal.length() + 1);
                    break;
                case "type":
                    var typeName = readTypeName(parts[1]);
                    type = new Type(Identifier.parse(typeName));
                    part = parts[1].substring(typeName.length() + 1);
                    break;
                case "value":
                    var initValueStr = parts[1].substring(0, parts[1].indexOf(")") + 1);
                    initValue = new Value(initValueStr);
                    part = parts[1].substring(initValueStr.length() + 1);
                    break;
                case END:
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
                case SLOT_ID:
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
                case END:
                    break parseOpts;
                default:
                    throw new IllegalArgumentException();
            }
            parts = part.trim().split(" ", 2);
        }
        return new TraitConst(qName, slotId, type, initValue);
    }
}
