package pdx.fo76.asasm;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class SyntaxConstants {
    public static final String METHOD = "method";
    public static final String TRAIT = "trait";
    public static final String CLASS = "class";
    public static final String SCRIPT = "script";
    public static final String REF_ID = "refid";
    public static final String SLOT_ID = "slotid";
    public static final String INSTANCE = "instance";
    public static final String Q_NAME = "QName";
    public static final String RTQ_NAME = "RTQName";
    public static final String EXTENDS = "extends";
    public static final String IMPLEMENTS = "implements";
    public static final String MULTINAME = "Multiname";
    public static final String MULTINAME_L = "MultinameL";
    public static final String MULTINAME_A = "MultinameA";
    public static final String NEW_FUNCTION = "newfunction";
    public static final String NEW_ACTIVATION = "newactivation";
    public static final String NEW_ARRAY = "newarray";
    public static final String NEW_OBJECT = "newobject";
    public static final String NEW_CATCH = "newcatch";
    public static final String THROW = "throw";
    public static final String NEW_CLASS = "newclass";
    public static final String COERCE = "coerce";
    public static final String COERCE_A = "coerce_a";
    public static final String COERCE_B = "coerce_b";
    public static final String COERCE_D = "coerce_d";
    public static final String COERCE_I = "coerce_i";
    public static final String COERCE_S = "coerce_s";
    public static final String COERCE_U = "coerce_U";
    public static final String CONVERT_A = "convert_a";
    public static final String CONVERT_B = "convert_b";
    public static final String CONVERT_D = "convert_d";
    public static final String CONVERT_I = "convert_i";
    public static final String CONVERT_S = "convert_s";
    public static final String CONVERT_U = "convert_u";
    public static final String SET_SLOT = "setslot";
    public static final String GET_SCOPE_OBJECT = "getscopeobject";
    public static final String APPLY_TYPE = "applytype";
    public static final String DUP = "dup";
    public static final String NULL_LITERAL = "null";
    public static final String NAN_LITERAL = "nan";
    public static final String UNDEFINED_LITERAL = "undefined";
    public static final String SET_LOCAL_0 = "setlocal0";
    public static final String SET_LOCAL_1 = "setlocal1";
    public static final String SET_LOCAL_2 = "setlocal2";
    public static final String SET_LOCAL_3 = "setlocal3";
    public static final String SET_LOCAL = "setlocal";
    public static final String GET_SLOT = "getslot";
    public static final String GET_GLOBAL_SCOPE = "getglobalscope";
    public static final String DIVIDE = "divide";
    public static final String MODULO = "modulo";
    public static final String MULTIPLY = "multiply";
    public static final String ADD = "add";
    public static final String SUBTRACT = "subtract";
    public static final String INCREMENT = "increment";
    public static final String INCREMENT_I = "increment_i";
    public static final String DECREMENT = "decrement";
    public static final String DECREMENT_I = "decrement_i";
    public static final String L_SHIFT = "lshift";
    public static final String R_SHIFT = "rshift";
    public static final String BIT_AND = "bitand";
    public static final String BIT_OR = "bitor";
    public static final String SWAP = "swap";
    public static final String NEGATE = "negate";
    public static final String EQUALS = "equals";
    public static final String GREATER_THAN = "greaterthan";
    public static final String GREATER_EQUALS = "greaterequals";
    public static final String LESS_THAN = "lessthan";
    public static final String LESS_EQUALS = "lessequals";
    public static final String NOT = "not";
    public static final String JUMP = "jump";
    public static final String IFEQ = "ifeq";
    public static final String IFNE = "ifne";
    public static final String IFGT = "ifgt";
    public static final String IFNGT = "ifngt";
    public static final String IFGE = "ifge";
    public static final String IFNGE = "ifnge";
    public static final String IFLT = "iflt";
    public static final String IFNLT = "ifnlt";
    public static final String IFLE = "ifle";
    public static final String IFNLE = "ifnle";
    public static final String IFTRUE = "iftrue";
    public static final String IFFALSE = "iffalse";
    public static final String IFSTRICTEQ = "ifstricteq";
    public static final String IFSTRICTNE = "ifstrictne";
    public static final String LABEL = "label";
    public static final String DECIMAL_DOT = ".";
    public static final String LABEL_COLON = ":";
    public static final String RETURNS = "returns";
    public static final String TYPEOF = "typeof";
    public static final String VALUE = "value";
    public static final String PARAM_NAME = "paramname";
    public static final String GETTER = "getter";
    public static final String SETTER = "setter";
    public static final String CONST = "const";
    public static final String OPTIONAL = "optional";
    public static final String VALUE_NULL = "Null";
    public static final String VALUE_INTEGER = "Integer";
    public static final String VALUE_DOUBLE = "Double";
    public static final String VALUE_UTF8 = "Utf8";
    public static final String VALUE_TRUE = "True";
    public static final String VALUE_FALSE = "False";
    public static final String KILL = "kill";
    public static final String LOOKUP_SWITCH = "lookupswitch";
    public static final String AS_TYPE_LATE = "astypelate";
    public static final String IS_TYPE_LATE = "istypelate";
    public static final String TRY = "try";
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String CHECKFILTER = "checkfilter";
    public static final String TARGET = "target";
    public static final String NEXT_VALUE = "nextvalue";
    public static final String NEXT_NAME = "nextname";
    public static final String HAS_NEXT_2 = "hasnext2";
    public static final String INC_LOCAL_I = "inclocal_i";
    public static final String DISP_ID = "dispid";
    public static final String PROGRAM = "program";
    public static final String MINOR_VERSION = "minorversion";
    public static final String MAJOR_VERSION = "majorversion";
    public static final String HASH_INCLUDE = "#include";
    public static final String HASH_VERSION = "#version";
    public static final String FLAG = "flag";
    public static final String PROTECTED_NS = "protectedns";
    public static final String IINIT = "iinit";
    public static final String CINIT = "cinit";
    public static final String SINIT = "sinit";
    public static final String NAME = "name";
    public static final String BODY = "body";
    public static final String CODE = "code";
    public static final String GET_LEX = "getlex";
    public static final String GET_LOCAL = "getlocal";
    public static final String GET_LOCAL_0 = "getlocal0";
    public static final String GET_LOCAL_1 = "getlocal1";
    public static final String GET_LOCAL_2 = "getlocal2";
    public static final String GET_LOCAL_3 = "getlocal3";
    public static final String GET_DESCENDANTS = "getdescendants";
    public static final String CONSTRUCT = "construct";
    public static final String CONSTRUCT_SUPER = "constructsuper";
    public static final String CONSTRUCT_PROP = "constructprop";
    public static final String INIT_PROPERTY = "initproperty";
    public static final String DELETE_PROPERTY = "deleteproperty";
    public static final String FIND_PROPERTY = "findproperty";
    public static final String FIND_PROP_STRICT = "findpropstrict";
    public static final String SET_PROPERTY = "setproperty";
    public static final String GET_PROPERTY = "getproperty";
    public static final String CALL = "call";
    public static final String CALL_PROPERTY = "callproperty";
    public static final String CALL_PROP_VOID = "callpropvoid";
    public static final String CALL_PROP_LEX = "callproplex";
    public static final String CALL_SUPER = "callsuper";
    public static final String CALL_SUPER_VOID = "callsupervoid";
    public static final String PROP = "prop";
    public static final String PUSH = "push";
    public static final String PUSH_STRING = "pushstring";
    public static final String PUSH_DOUBLE = "pushdouble";
    public static final String PUSH_BYTE = "pushbyte";
    public static final String PUSH_SHORT = "pushshort";
    public static final String PUSH_NULL = "pushnull";
    public static final String PUSH_TRUE = "pushtrue";
    public static final String PUSH_FALSE = "pushfalse";
    public static final String PUSH_SCOPE = "pushscope";
    public static final String PUSH_NAN = "pushnan";
    public static final String PUSH_INT = "pushint";
    public static final String PUSH_WITH = "pushwith";
    public static final String POP = "pop";
    public static final String POP_SCOPE = "popscope";
    public static final String DEBUG = "debug";
    public static final String DEBUG_FILE = "debugfile";
    public static final String DEBUG_LINE = "debugline";
    public static final String MAX_STACK = "maxstack";
    public static final String LOCAL_COUNT = "localcount";
    public static final String INIT_SCOPE_DEPTH = "initscopedepth";
    public static final String MAX_SCOPE_DEPTH = "maxscopedepth";
    public static final String TYPENAME = "TypeName";
    public static final String NAMESPACE = "Namespace";
    public static final String PRIVATE_NAMESPACE = "PrivateNamespace";
    public static final String PROTECTED_NAMESPACE = "ProtectedNamespace";
    public static final String PACKAGE_NAMESPACE = "PackageNamespace";
    public static final String PACKAGE_INTERNAL_NS = "PackageInternalNs";
    public static final String STATIC_PROTECTED_NS = "StaticProtectedNs";
    public static final String RETURN_VOID = "returnvoid";
    public static final String RETURN_VALUE = "returnvalue";
    public static final String END = "end";
}
