package pdx.fo76.asasm.token;

import java.util.regex.Pattern;

public enum Token {
    BLANK_LINE(none()),
    CLASS(literal("class")),
    SCRIPT(literal("script")),
    REFID(literal("refid")),
    STRING_LITERAL(stringLiteral()),
    INSTANCE(literal("instance")),
    QNAME(literal("QName")),
    RTQNAME(literal("RTQName")),
    OPEN_P(literal("(")),
    CLOSE_P(literal(")")),
    COMMA(literal(",")),
    EXTENDS(literal("extends")),
    FLAG(literal("flag")),
    CONSTANT(regex("[A-Z_]{5,}")),
    PROTECTEDNS(literal("protectedns")),
    NS_PRIVATE(literal("PrivateNamespace")),
    NS_PROTECTED(literal("ProtectedNamespace")),
    NS_PACKAGE(literal("PackageNamespace")),
    NS_STATIC_PROTECTED(literal("StaticProtectedNs")),
    NS_PACKAGE_INTERNAL(literal("PackageInternalNs")),
    NAMESPACE(literal("Namespace")),
    TYPENAME(literal("TypeName")),
    IINIT(literal("iinit")),
    CINIT(literal("cinit")),
    SINIT(literal("sinit")),
    NAME(literal("name")),
    BODY(literal("body")),
    MAX_STACK(literal("maxstack")),
    FLOAT_LITERAL(regex("-?[0-9]+\\.[0-9]+(e(\\+|-)[0-9]+)?")),
    INT_LITERAL(regex("-?[0-9]+(?!\\.)")),
    LOCAL_COUNT(literal("localcount")),
    INIT_SCOPE_DEPTH(literal("initscopedepth")),
    MAX_SCOPE_DEPTH(literal("maxscopedepth")),
    CODE(literal("code")),
    DEBUG_FILE(literal("debugfile")),
    DEBUG_LINE(literal("debugline")),
    DEBUG(literal("debug")),
    GET_LOCAL_0(literal("getlocal0")),
    GET_LOCAL_1(literal("getlocal1")),
    GET_LOCAL_2(literal("getlocal2")),
    GET_LOCAL_3(literal("getlocal3")),
    GET_LOCAL(regex("getlocal(?![0123])")),
    GET_DESCENDANTS(regex("getdescendants")),
    DELETE_PROPERTY(literal("deleteproperty")),
    FIND_PROPERTY(literal("findproperty")),
    FIND_PROP_STRICT(literal("findpropstrict")),
    PUSH_STRING(literal("pushstring")),
    PUSH_DOUBLE(literal("pushdouble")),
    PUSH_SHORT(literal("pushshort")),
    PUSH_BYTE(literal("pushbyte")),
    PUSH_NULL(literal("pushnull")),
    PUSH_TRUE(literal("pushtrue")),
    PUSH_FALSE(literal("pushfalse")),
    PUSH_SCOPE(literal("pushscope")),
    PUSH_NAN(literal("pushnan")),
    PUSH_INT(literal("pushint")),
    PUSH_WITH(literal("pushwith")),
    PUSH(regex("push(?!(scope|null|byte|short|double|true|false|string|nan|int|with))")),
    POP(regex("pop(?!(scope|null|byte|short|double|true|false|string|nan|int|with))")),
    POP_SCOPE(literal("popscope")),
    PROP(literal("prop")),
    CONSTRUCT(regex("construct(?!(super|prop))")),
    CONSTRUCT_SUPER(literal("constructsuper")),
    CONSTRUCT_PROP(literal("constructprop")),
    INIT_PROPERTY(literal("initproperty")),
    GET_LEX(literal("getlex")),
    SET_PROPERTY(literal("setproperty")),
    GET_PROPERTY(literal("getproperty")),
    CALL_PROPERTY(literal("callproperty")),
    CALL_PROP_VOID(literal("callpropvoid")),
    CALL_PROP_LEX(literal("callproplex")),
    CALL_SUPER(regex("callsuper(?!void)")),
    CALL_SUPER_VOID(literal("callsupervoid")),
    CALL(regex("call(?!(super|prop))")),
    IMPLEMENTS(literal("implements")),
    MULTINAME(regex("Multiname(?!(L|A))")),
    MULTINAME_L(literal("MultinameL")),
    MULTINAME_A(literal("MultinameA")),
    OPEN_B(literal("[")),
    CLOSE_B(literal("]")),
    OPEN_T(literal("<")),
    CLOSE_T(literal(">")),
    NEW_FUNCTION(literal("newfunction")),
    NEW_ACTIVATION(literal("newactivation")),
    NEW_ARRAY(literal("newarray")),
    NEW_OBJECT(literal("newobject")),
    NEW_CATCH(literal("newcatch")),
    THROW(literal("throw")),
    NEW_CLASS(literal("newclass")),
    COERCE(regex("coerce(?!\\_)")),
    COERCE_A(literal("coerce_a")),
    COERCE_B(literal("coerce_b")),
    COERCE_D(literal("coerce_d")),
    COERCE_I(literal("coerce_i")),
    COERCE_S(literal("coerce_s")),
    COERCE_U(literal("coerce_U")),
    CONVERT_A(literal("convert_a")),
    CONVERT_B(literal("convert_b")),
    CONVERT_D(literal("convert_d")),
    CONVERT_I(literal("convert_i")),
    CONVERT_S(literal("convert_s")),
    CONVERT_U(literal("convert_u")),
    SET_SLOT(literal("setslot")),
    GET_SCOPE_OBJECT(literal("getscopeobject")),
    APPLY_TYPE(literal("applytype")),
    DUP(literal("dup")),
    NULL_LITERAL(literal("null")),
    NAN_LITERAL(literal("nan")),
    UNDEFINED_LITERAL(literal("undefined")),
    SET_LOCAL_0(literal("setlocal0")),
    SET_LOCAL_1(literal("setlocal1")),
    SET_LOCAL_2(literal("setlocal2")),
    SET_LOCAL_3(literal("setlocal3")),
    SET_LOCAL(regex("setlocal(?![0123])")),
    GET_SLOT(literal("getslot")),
    GET_GLOBAL_SCOPE(literal("getglobalscope")),
    DIVIDE(literal("divide")),
    MODULO(literal("modulo")),
    MULTIPLY(literal("multiply")),
    ADD(literal("add")),
    SUBTRACT(literal("subtract")),
    INCREMENT(regex("increment(?!\\_)")),
    INCREMENT_I(literal("increment_i")),
    DECREMENT(regex("decrement(?!\\_)")),
    DECREMENT_I(literal("decrement_i")),
    L_SHIFT(literal("lshift")),
    R_SHIFT(literal("rshift")),
    BIT_AND(literal("bitand")),
    BIT_OR(literal("bitor")),
    SWAP(literal("swap")),
    NEGATE(literal("negate")),
    EQUALS(literal("equals")),
    GREATER_THAN(literal("greaterthan")),
    GREATER_EQUALS(literal("greaterequals")),
    LESS_THAN(literal("lessthan")),
    LESS_EQUALS(literal("lessequals")),
    NOT(literal("not")),
    JUMP(literal("jump")),
    IFEQ(literal("ifeq")),
    IFNE(literal("ifne")),
    IFGT(literal("ifgt")),
    IFNGT(literal("ifngt")),
    IFGE(literal("ifge")),
    IFNGE(literal("ifnge")),
    IFLT(literal("iflt")),
    IFNLT(literal("ifnlt")),
    IFLE(literal("ifle")),
    IFNLE(literal("ifnle")),
    IFTRUE(literal("iftrue")),
    IFFALSE(literal("iffalse")),
    IFSTRICTEQ(literal("ifstricteq")),
    IFSTRICTNE(literal("ifstrictne")),
    JUMP_LABEL(regex("L[0-9]+")),
    LABEL(literal("label")),
    DECIMAL_DOT(literal(".")),
    LABEL_COLON(literal(":")),
    RETURNS(literal("returns")),
    RETURN_VOID(literal("returnvoid")),
    RETURN_VALUE(literal("returnvalue")),
    END(literal("end")),
    COMMENT(regex(";.+")),
    TRAIT(literal("trait")),
    SLOT(regex("slot(?!id)")),
    SLOT_ID(literal("slotid")),
    TYPE(regex("type(?!of)")),
    TYPEOF(literal("typeof")),
    VALUE(literal("value")),
    METHOD(literal("method")),
    PARAM(regex("param(?!name)")),
    PARAM_NAME(literal("paramname")),
    GETTER(literal("getter")),
    SETTER(literal("setter")),
    CONST(literal("const")),
    OPTIONAL(literal("optional")),
    VALUE_NULL(literal("Null")),
    VALUE_INTEGER(literal("Integer")),
    VALUE_DOUBLE(literal("Double")),
    VALUE_UTF8(literal("Utf8")),
    VALUE_TRUE(literal("True")),
    VALUE_FALSE(literal("False")),
    KILL(literal("kill")),
    LOOKUP_SWITCH(literal("lookupswitch")),
    AS_TYPE_LATE(literal("astypelate")),
    IS_TYPE_LATE(literal("istypelate")),
    TRY(literal("try")),
    FROM(literal("from")),
    TO(literal("to")),
    IN(regex("in(?!clocal)")),
    CHECKFILTER(literal("checkfilter")),
    TARGET(literal("target")),
    NEXT_VALUE(literal("nextvalue")),
    NEXT_NAME(literal("nextname")),
    HAS_NEXT_2(literal("hasnext2")),
    INC_LOCAL_I(literal("inclocal_i")),
    DISP_ID(literal("dispid")),
    PROGRAM(literal("program")),
    MINOR_VERSION(literal("minorversion")),
    MAJOR_VERSION(literal("majorversion")),
    HASH_INCLUDE(literal("#include")),
    HASH_VERSION(literal("#version")),

    ;


    private final Tokenizer tokenizer;

    Token(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public ParsedToken read(String line) {
        return tokenizer.read(this, line);
    }

    static Tokenizer none() {
        return (t, line) -> null;
    }

    static Tokenizer literal(String value) {
        return (t, line) -> {
            if (line.startsWith(value)) {
                return new ParsedToken(t, value);
            }
            return null;
        };
    }

    static Tokenizer stringLiteral() {
        return (t, line) -> {
            if (!line.startsWith("\"")) {
                return null;
            }
            boolean escape = false;
            for (int i = 1; i < line.length(); i++) {
                if (!escape && line.charAt(i) == '\\') {
                    escape = true;
                } else if (escape) {
                    escape = false;
                } else if (line.charAt(i) == '"' && !escape) {
                    return new ParsedToken(t, line.substring(0, i + 1));
                }
            }
            return null;
        };
    }

    static Tokenizer regex(String pattern) {
        var compiled = Pattern.compile("^" + pattern);
        return (t, line) -> {
            var matcher = compiled.matcher(line);
            if (matcher.find()) {
                return new ParsedToken(t, matcher.group());
            }
            return null;
        };
    }
}
