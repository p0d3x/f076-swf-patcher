package pdx.fo76.asasm.token;

import pdx.fo76.asasm.SyntaxConstants;

import java.util.regex.Pattern;

public enum Token {
    BLANK_LINE(none()),
    CLASS(literal(SyntaxConstants.CLASS)),
    SCRIPT(literal(SyntaxConstants.SCRIPT)),
    REFID(literal(SyntaxConstants.REF_ID)),
    STRING_LITERAL(stringLiteral()),
    INSTANCE(literal(SyntaxConstants.INSTANCE)),
    QNAME(literal(SyntaxConstants.Q_NAME)),
    RTQNAME(literal(SyntaxConstants.RTQ_NAME)),
    OPEN_P(literal("(")),
    CLOSE_P(literal(")")),
    COMMA(literal(",")),
    EXTENDS(literal(SyntaxConstants.EXTENDS)),
    FLAG(literal(SyntaxConstants.FLAG)),
    CONSTANT(regex("[A-Z_]{5,}")),
    PROTECTEDNS(literal(SyntaxConstants.PROTECTED_NS)),
    NS_PRIVATE(literal(SyntaxConstants.PRIVATE_NAMESPACE)),
    NS_PROTECTED(literal(SyntaxConstants.PROTECTED_NAMESPACE)),
    NS_PACKAGE(literal(SyntaxConstants.PACKAGE_NAMESPACE)),
    NS_STATIC_PROTECTED(literal(SyntaxConstants.STATIC_PROTECTED_NS)),
    NS_PACKAGE_INTERNAL(literal(SyntaxConstants.PACKAGE_INTERNAL_NS)),
    NAMESPACE(literal(SyntaxConstants.NAMESPACE)),
    TYPENAME(literal(SyntaxConstants.TYPENAME)),
    IINIT(literal(SyntaxConstants.IINIT)),
    CINIT(literal(SyntaxConstants.CINIT)),
    SINIT(literal(SyntaxConstants.SINIT)),
    NAME(literal(SyntaxConstants.NAME)),
    BODY(literal(SyntaxConstants.BODY)),
    MAX_STACK(literal(SyntaxConstants.MAX_STACK)),
    FLOAT_LITERAL(regex("-?[0-9]+\\.[0-9]+(e(\\+|-)[0-9]+)?")),
    INT_LITERAL(regex("-?[0-9]+(?!\\.)")),
    LOCAL_COUNT(literal(SyntaxConstants.LOCAL_COUNT)),
    INIT_SCOPE_DEPTH(literal(SyntaxConstants.INIT_SCOPE_DEPTH)),
    MAX_SCOPE_DEPTH(literal(SyntaxConstants.MAX_SCOPE_DEPTH)),
    CODE(literal(SyntaxConstants.CODE)),
    DEBUG_FILE(literal(SyntaxConstants.DEBUG_FILE)),
    DEBUG_LINE(literal(SyntaxConstants.DEBUG_LINE)),
    DEBUG(literal(SyntaxConstants.DEBUG)),
    GET_LOCAL_0(literal(SyntaxConstants.GET_LOCAL_0)),
    GET_LOCAL_1(literal(SyntaxConstants.GET_LOCAL_1)),
    GET_LOCAL_2(literal(SyntaxConstants.GET_LOCAL_2)),
    GET_LOCAL_3(literal(SyntaxConstants.GET_LOCAL_3)),
    GET_LOCAL(regex(SyntaxConstants.GET_LOCAL + "(?![0123])")),
    GET_DESCENDANTS(regex(SyntaxConstants.GET_DESCENDANTS)),
    DELETE_PROPERTY(literal(SyntaxConstants.DELETE_PROPERTY)),
    FIND_PROPERTY(literal(SyntaxConstants.FIND_PROPERTY)),
    FIND_PROP_STRICT(literal(SyntaxConstants.FIND_PROP_STRICT)),
    PUSH_STRING(literal(SyntaxConstants.PUSH_STRING)),
    PUSH_DOUBLE(literal(SyntaxConstants.PUSH_DOUBLE)),
    PUSH_SHORT(literal(SyntaxConstants.PUSH_SHORT)),
    PUSH_BYTE(literal(SyntaxConstants.PUSH_BYTE)),
    PUSH_NULL(literal(SyntaxConstants.PUSH_NULL)),
    PUSH_TRUE(literal(SyntaxConstants.PUSH_TRUE)),
    PUSH_FALSE(literal(SyntaxConstants.PUSH_FALSE)),
    PUSH_SCOPE(literal(SyntaxConstants.PUSH_SCOPE)),
    PUSH_NAN(literal(SyntaxConstants.PUSH_NAN)),
    PUSH_INT(literal(SyntaxConstants.PUSH_INT)),
    PUSH_WITH(literal(SyntaxConstants.PUSH_WITH)),
    PUSH(regex(SyntaxConstants.PUSH + "(?!(scope|null|byte|short|double|true|false|string|nan|int|with))")),
    POP(regex(SyntaxConstants.POP + "(?!(scope|null|byte|short|double|true|false|string|nan|int|with))")),
    POP_SCOPE(literal(SyntaxConstants.POP_SCOPE)),
    PROP(literal(SyntaxConstants.PROP)),
    CONSTRUCT(regex(SyntaxConstants.CONSTRUCT + "(?!(super|prop))")),
    CONSTRUCT_SUPER(literal(SyntaxConstants.CONSTRUCT_SUPER)),
    CONSTRUCT_PROP(literal(SyntaxConstants.CONSTRUCT_PROP)),
    INIT_PROPERTY(literal(SyntaxConstants.INIT_PROPERTY)),
    GET_LEX(literal(SyntaxConstants.GET_LEX)),
    SET_PROPERTY(literal(SyntaxConstants.SET_PROPERTY)),
    GET_PROPERTY(literal(SyntaxConstants.GET_PROPERTY)),
    CALL_PROPERTY(literal(SyntaxConstants.CALL_PROPERTY)),
    CALL_PROP_VOID(literal(SyntaxConstants.CALL_PROP_VOID)),
    CALL_PROP_LEX(literal(SyntaxConstants.CALL_PROP_LEX)),
    CALL_SUPER(regex(SyntaxConstants.CALL_SUPER + "(?!void)")),
    CALL_SUPER_VOID(literal(SyntaxConstants.CALL_SUPER_VOID)),
    CALL(regex(SyntaxConstants.CALL + "(?!(super|prop))")),
    IMPLEMENTS(literal(SyntaxConstants.IMPLEMENTS)),
    MULTINAME(regex(SyntaxConstants.MULTINAME + "(?!(L|A))")),
    MULTINAME_L(literal(SyntaxConstants.MULTINAME_L)),
    MULTINAME_A(literal(SyntaxConstants.MULTINAME_A)),
    OPEN_B(literal("[")),
    CLOSE_B(literal("]")),
    OPEN_T(literal("<")),
    CLOSE_T(literal(">")),
    NEW_FUNCTION(literal(SyntaxConstants.NEW_FUNCTION)),
    NEW_ACTIVATION(literal(SyntaxConstants.NEW_ACTIVATION)),
    NEW_ARRAY(literal(SyntaxConstants.NEW_ARRAY)),
    NEW_OBJECT(literal(SyntaxConstants.NEW_OBJECT)),
    NEW_CATCH(literal(SyntaxConstants.NEW_CATCH)),
    THROW(literal(SyntaxConstants.THROW)),
    NEW_CLASS(literal(SyntaxConstants.NEW_CLASS)),
    COERCE(regex(SyntaxConstants.COERCE + "(?!\\_)")),
    COERCE_A(literal(SyntaxConstants.COERCE_A)),
    COERCE_B(literal(SyntaxConstants.COERCE_B)),
    COERCE_D(literal(SyntaxConstants.COERCE_D)),
    COERCE_I(literal(SyntaxConstants.COERCE_I)),
    COERCE_S(literal(SyntaxConstants.COERCE_S)),
    COERCE_U(literal(SyntaxConstants.COERCE_U)),
    CONVERT_A(literal(SyntaxConstants.CONVERT_A)),
    CONVERT_B(literal(SyntaxConstants.CONVERT_B)),
    CONVERT_D(literal(SyntaxConstants.CONVERT_D)),
    CONVERT_I(literal(SyntaxConstants.CONVERT_I)),
    CONVERT_S(literal(SyntaxConstants.CONVERT_S)),
    CONVERT_U(literal(SyntaxConstants.CONVERT_U)),
    SET_SLOT(literal(SyntaxConstants.SET_SLOT)),
    GET_SCOPE_OBJECT(literal(SyntaxConstants.GET_SCOPE_OBJECT)),
    APPLY_TYPE(literal(SyntaxConstants.APPLY_TYPE)),
    DUP(literal(SyntaxConstants.DUP)),
    NULL_LITERAL(literal(SyntaxConstants.NULL_LITERAL)),
    NAN_LITERAL(literal(SyntaxConstants.NAN_LITERAL)),
    UNDEFINED_LITERAL(literal(SyntaxConstants.UNDEFINED_LITERAL)),
    SET_LOCAL_0(literal(SyntaxConstants.SET_LOCAL_0)),
    SET_LOCAL_1(literal(SyntaxConstants.SET_LOCAL_1)),
    SET_LOCAL_2(literal(SyntaxConstants.SET_LOCAL_2)),
    SET_LOCAL_3(literal(SyntaxConstants.SET_LOCAL_3)),
    SET_LOCAL(regex(SyntaxConstants.SET_LOCAL + "(?![0123])")),
    GET_SLOT(literal(SyntaxConstants.GET_SLOT)),
    GET_GLOBAL_SCOPE(literal(SyntaxConstants.GET_GLOBAL_SCOPE)),
    DIVIDE(literal(SyntaxConstants.DIVIDE)),
    MODULO(literal(SyntaxConstants.MODULO)),
    MULTIPLY(literal(SyntaxConstants.MULTIPLY)),
    ADD(literal(SyntaxConstants.ADD)),
    SUBTRACT(literal(SyntaxConstants.SUBTRACT)),
    INCREMENT(regex(SyntaxConstants.INCREMENT + "(?!\\_)")),
    INCREMENT_I(literal(SyntaxConstants.INCREMENT_I)),
    DECREMENT(regex(SyntaxConstants.DECREMENT + "(?!\\_)")),
    DECREMENT_I(literal(SyntaxConstants.DECREMENT_I)),
    L_SHIFT(literal(SyntaxConstants.L_SHIFT)),
    R_SHIFT(literal(SyntaxConstants.R_SHIFT)),
    BIT_AND(literal(SyntaxConstants.BIT_AND)),
    BIT_OR(literal(SyntaxConstants.BIT_OR)),
    SWAP(literal(SyntaxConstants.SWAP)),
    NEGATE(literal(SyntaxConstants.NEGATE)),
    EQUALS(literal(SyntaxConstants.EQUALS)),
    GREATER_THAN(literal(SyntaxConstants.GREATER_THAN)),
    GREATER_EQUALS(literal(SyntaxConstants.GREATER_EQUALS)),
    LESS_THAN(literal(SyntaxConstants.LESS_THAN)),
    LESS_EQUALS(literal(SyntaxConstants.LESS_EQUALS)),
    NOT(literal(SyntaxConstants.NOT)),
    JUMP(literal(SyntaxConstants.JUMP)),
    IFEQ(literal(SyntaxConstants.IFEQ)),
    IFNE(literal(SyntaxConstants.IFNE)),
    IFGT(literal(SyntaxConstants.IFGT)),
    IFNGT(literal(SyntaxConstants.IFNGT)),
    IFGE(literal(SyntaxConstants.IFGE)),
    IFNGE(literal(SyntaxConstants.IFNGE)),
    IFLT(literal(SyntaxConstants.IFLT)),
    IFNLT(literal(SyntaxConstants.IFNLT)),
    IFLE(literal(SyntaxConstants.IFLE)),
    IFNLE(literal(SyntaxConstants.IFNLE)),
    IFTRUE(literal(SyntaxConstants.IFTRUE)),
    IFFALSE(literal(SyntaxConstants.IFFALSE)),
    IFSTRICTEQ(literal(SyntaxConstants.IFSTRICTEQ)),
    IFSTRICTNE(literal(SyntaxConstants.IFSTRICTNE)),
    JUMP_LABEL(regex("L[0-9]+")),
    LABEL(literal(SyntaxConstants.LABEL)),
    DECIMAL_DOT(literal(SyntaxConstants.DECIMAL_DOT)),
    LABEL_COLON(literal(SyntaxConstants.LABEL_COLON)),
    RETURNS(literal(SyntaxConstants.RETURNS)),
    RETURN_VOID(literal(SyntaxConstants.RETURN_VOID)),
    RETURN_VALUE(literal(SyntaxConstants.RETURN_VALUE)),
    END(literal(SyntaxConstants.END)),
    COMMENT(regex(";.+")),
    TRAIT(literal(SyntaxConstants.TRAIT)),
    SLOT(regex("slot(?!id)")),
    SLOT_ID(literal(SyntaxConstants.SLOT_ID)),
    TYPE(regex("type(?!of)")),
    TYPEOF(literal(SyntaxConstants.TYPEOF)),
    VALUE(literal(SyntaxConstants.VALUE)),
    METHOD(literal(SyntaxConstants.METHOD)),
    PARAM(regex("param(?!name)")),
    PARAM_NAME(literal(SyntaxConstants.PARAM_NAME)),
    GETTER(literal(SyntaxConstants.GETTER)),
    SETTER(literal(SyntaxConstants.SETTER)),
    CONST(literal(SyntaxConstants.CONST)),
    OPTIONAL(literal(SyntaxConstants.OPTIONAL)),
    VALUE_NULL(literal(SyntaxConstants.VALUE_NULL)),
    VALUE_INTEGER(literal(SyntaxConstants.VALUE_INTEGER)),
    VALUE_DOUBLE(literal(SyntaxConstants.VALUE_DOUBLE)),
    VALUE_UTF8(literal(SyntaxConstants.VALUE_UTF8)),
    VALUE_TRUE(literal(SyntaxConstants.VALUE_TRUE)),
    VALUE_FALSE(literal(SyntaxConstants.VALUE_FALSE)),
    KILL(literal(SyntaxConstants.KILL)),
    LOOKUP_SWITCH(literal(SyntaxConstants.LOOKUP_SWITCH)),
    AS_TYPE_LATE(literal(SyntaxConstants.AS_TYPE_LATE)),
    IS_TYPE_LATE(literal(SyntaxConstants.IS_TYPE_LATE)),
    TRY(literal(SyntaxConstants.TRY)),
    FROM(literal(SyntaxConstants.FROM)),
    TO(literal(SyntaxConstants.TO)),
    IN(regex("in(?!clocal)")),
    CHECKFILTER(literal(SyntaxConstants.CHECKFILTER)),
    TARGET(literal(SyntaxConstants.TARGET)),
    NEXT_VALUE(literal(SyntaxConstants.NEXT_VALUE)),
    NEXT_NAME(literal(SyntaxConstants.NEXT_NAME)),
    HAS_NEXT_2(literal(SyntaxConstants.HAS_NEXT_2)),
    INC_LOCAL_I(literal(SyntaxConstants.INC_LOCAL_I)),
    DISP_ID(literal(SyntaxConstants.DISP_ID)),
    PROGRAM(literal(SyntaxConstants.PROGRAM)),
    MINOR_VERSION(literal(SyntaxConstants.MINOR_VERSION)),
    MAJOR_VERSION(literal(SyntaxConstants.MAJOR_VERSION)),
    HASH_INCLUDE(literal(SyntaxConstants.HASH_INCLUDE)),
    HASH_VERSION(literal(SyntaxConstants.HASH_VERSION)),
    ;

    private final Tokenizer tokenizer;

    Token(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public ParsedToken read(String line, int lineNum, int linePos) {
        return tokenizer.read(this, line, lineNum, linePos);
    }

    static Tokenizer none() {
        return (t, line, lineNum, linePos) -> null;
    }

    static Tokenizer literal(String value) {
        return (t, line, lineNum, linePos) -> {
            if (line.startsWith(value)) {
                return new ParsedToken(t, value, lineNum, linePos);
            }
            return null;
        };
    }

    static Tokenizer stringLiteral() {
        return (t, line, lineNum, linePos) -> {
            if (!line.startsWith("\"")) {
                return null;
            }
            boolean escape = false;
            for (int i = 1; i < line.length(); i++) {
                if (!escape && line.charAt(i) == '\\') {
                    escape = true;
                } else if (escape) {
                    escape = false;
                } else if (line.charAt(i) == '"') {
                    return new ParsedToken(t, line.substring(0, i + 1), lineNum, linePos);
                }
            }
            return null;
        };
    }

    static Tokenizer regex(String pattern) {
        var compiled = Pattern.compile("^" + pattern);
        return (t, line, lineNum, linePos)  -> {
            var matcher = compiled.matcher(line);
            if (matcher.find()) {
                return new ParsedToken(t, matcher.group(), lineNum, linePos);
            }
            return null;
        };
    }
}
