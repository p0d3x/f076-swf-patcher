package pdx.fo76.asasm.syntax;

import pdx.fo76.asasm.token.ParsedToken;
import pdx.fo76.asasm.token.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static pdx.fo76.asasm.token.Token.*;

public interface Parser<T extends Syntax> {

    Either<Syntax, ParseStop> parse(List<ParsedToken> tokens, int pos);

    default Parser<Syntax> then(Token... options) {
        return then(of(options));
    }

    default Parser<Syntax> then(Parser<? extends Syntax>... options) {
        return (tokens, pos) -> {
            var first = parse(tokens, pos);
            if (first.isRight()) {
                return first;
            }
            var cur = pos + first.left().getLength();
            var result = new ArrayList<Syntax>();
            var bestDepth = cur;
            for (int i = 0; i < options.length; i++) {
                Parser<? extends Syntax> option = options[i];
                var res = option.parse(tokens, cur);
                if (res.isLeft()) {
                    cur += res.left().getLength();
                    result.add(res.left());
                    i = 0;
                    bestDepth = cur;
                } else {
                    bestDepth = Math.max(cur, res.right().getPosition());
                }
            }
            if (result.isEmpty()) {
                return Either.right(new ParseStop(bestDepth));
            }
            result.add(0, first.left());
            return Either.left(Syntax.multi(result));
        };
    }

    default <V extends Syntax> Parser<V> transform(Parser<Syntax> peek, Transformer<V> transformer) {
        return (tokens, pos) -> {
            var first = parse(tokens, pos);
            if (first.isRight()) {
                return first;
            }
            var cur = pos + first.left().getLength();
            var peekResult = peek.parse(tokens, cur);
            if (peekResult.isRight()) {
                return peekResult;
            }
            return Either.left(transformer.transform(first.left(), peekResult.left().getLength()));
        };
    }

    default <V extends Syntax> Parser<V> transform(Transformer<V> transformer) {
        return (tokens, pos) -> {
            var first = parse(tokens, pos);
            if (first.isRight()) {
                return first;
            }
            return Either.left(transformer.transform(first.left()));
        };
    }

    default Parser<Syntax> or(Parser<? extends Syntax> other) {
        return (tokens, pos) -> {
            var first = parse(tokens, pos);
            if (first.isLeft()) {
                return first;
            }
            var second = other.parse(tokens, pos);
            if (second.isLeft()) {
                return second;
            }
            return first.right().getPosition() >= second.right().getPosition() ? first : second;
        };
    }

    default Parser<Syntax> optional(Token... tokens) {
        return optional(of(tokens));
    }

    default Parser<Syntax> optional(Parser<? extends Syntax> optional) {
        return (tokens, pos) -> {
            var first = parse(tokens, pos);
            if (first.isRight()) {
                return first;
            }
            var second = optional.parse(tokens, pos + first.left().getLength());
            if (second.isRight()) {
                return first;
            }
            return Either.left(Syntax.multi(List.of(first.left(), second.left())));
        };
    }

    static Parser<Syntax> of(Token... sequence) {
        return of(Arrays.asList(sequence));
    }

    static <T extends Syntax> Parser<T> of(Function<List<ParsedToken>, T> mapper, Token... sequence) {
        return of(mapper, Arrays.asList(sequence));
    }

    static Parser<Syntax> of(List<Token> sequence) {
        return of(Syntax::of, List.of(), sequence);
    }

    static <T extends Syntax> Parser<T> of(Function<List<ParsedToken>, T> mapper, List<Token> sequence) {
        return of(mapper, List.of(), sequence);
    }

    static <T extends Syntax> Parser<T> of(Function<List<ParsedToken>, T> mapper,
                                           List<Token> options,
                                           List<Token> sequence) {
        return (tokens, pos) -> {
            if (tokens.size() < sequence.size() + pos + (options.isEmpty() ? 0 : 1)) {
                return Either.right(new ParseStop(pos));
            }
            if (!options.isEmpty() && !options.contains(tokens.get(pos).getToken())) {
                return Either.right(new ParseStop(pos));
            }
            var offs = options.isEmpty() ? 0 : 1;
            if (!sequence.isEmpty()) {
                for (int i = 0; i < sequence.size(); i++) {
                    if (tokens.get(pos + offs + i).getToken() != sequence.get(i)) {
                        return Either.right(new ParseStop(pos + offs + i));
                    }
                }
            }
            return Either.left(mapper.apply(tokens.subList(pos, pos + offs + sequence.size())));
        };
    }

    static Parser<Syntax> more(Parser<? extends Syntax> parser) {
        return more(parser, null);
    }

    static Parser<Syntax> more(Parser<? extends Syntax> parser, Parser<? extends Syntax> separator) {
        return (tokens, pos) -> {
            var first = parser.parse(tokens, pos);
            if (first.isRight()) {
                return first;
            }
            var cur = pos + first.left().getLength();
            var result = new ArrayList<Syntax>();
            result.add(first.left());
            Parser<? extends Syntax> nextParser;
            if (separator != null) {
                nextParser = separator.then(parser);
            } else {
                nextParser = parser;
            }
            var next = nextParser.parse(tokens, cur);
            while (next.isLeft()) {
                result.add(next.left());
                cur += next.left().getLength();
                next = nextParser.parse(tokens, cur);
            }
            return Either.left(Syntax.multi(result));
        };
    }

    static Parser<Syntax> oneOf(Token... tokens) {
        return of(Syntax::of, Arrays.asList(tokens), List.of());
    }

    static Parser<Syntax> oneOf(Parser<Syntax>... parsers) {
        return (tokens, pos) -> {
            var bestPos = pos;
            for (Parser<Syntax> parser : parsers) {
                var result = parser.parse(tokens, pos);
                if (result.isLeft()) {
                    return result;
                } else {
                    bestPos = Math.max(result.right().getPosition(), bestPos);
                }
            }
            return Either.right(new ParseStop(bestPos));
        };
    }

    static Parser<Syntax.ScriptS> script() {
        return of(SCRIPT)
                .then(sinit(), trait())
                .transform(of(END, COMMENT), Syntax.ScriptS::transform);
    }

    static Parser<Syntax.ClassS> clazz() {
        return of(CLASS)
                .then(refid(), instance(), cinit(), trait())
                .transform(of(END, COMMENT), Syntax.ClassS::transform);
    }
    
    static Parser<Syntax.RefIdS> refid() {
        return of(Syntax.RefIdS::of, List.of(REFID, STRING_LITERAL));
    }

    static Parser<Syntax.DispIdS> dispid() {
        return of(Syntax.DispIdS::of, List.of(DISP_ID, INT_LITERAL));
    }
    
    static Parser<Syntax> instance() {
        return of(INSTANCE).then(qName())
                .then(extend(), implement(), flag(), protectedns(), iinit(), trait())
                .then(END, COMMENT);
    }
    
    static Parser<Syntax.QNameS> qName() {
        return of(QNAME, OPEN_P).then(namespace()).then(COMMA, STRING_LITERAL, CLOSE_P)
                .transform(Syntax.QNameS::transform);
    }

    static Parser<Syntax.RTQNameS> rtqName() {
        return of(Syntax.RTQNameS::of, List.of(RTQNAME, OPEN_P, STRING_LITERAL, CLOSE_P));
    }
    
    static Parser<Syntax> namespace() {
        return of(Syntax.NamespaceS::of,
                List.of(NAMESPACE,
                        NS_PRIVATE,
                        NS_PACKAGE,
                        NS_PROTECTED,
                        NS_PACKAGE_INTERNAL,
                        NS_STATIC_PROTECTED),
                List.of(OPEN_P, STRING_LITERAL, CLOSE_P)
        );
    }

    static Parser<Syntax> extend() {
        return of(EXTENDS).then(qName());
    }

    static Parser<Syntax> implement() {
        return of(IMPLEMENTS).then(multiname());
    }
    
    static Parser<Syntax.FlagS> flag() {
        return of(Syntax.FlagS::of, FLAG, CONSTANT);
    }
    
    static Parser<Syntax> protectedns() {
        return of(PROTECTEDNS).then(namespace());
    }
    
    static Parser<Syntax.MethodS> iinit() {
        return of(IINIT)
                .then(name(), refid(), param(), paramname(), returns(), flag(), body(), optionalValue())
                .transform(of(END, COMMENT), Syntax.MethodS::transform);
    }

    static Parser<Syntax.MethodS> cinit() {
        return of(CINIT)
                .then(name(), refid(), param(), paramname(), returns(), flag(), body(), optionalValue())
                .transform(of(END, COMMENT), Syntax.MethodS::transform);
    }

    static Parser<Syntax.MethodS> sinit() {
        return of(SINIT)
                .then(name(), refid(), param(), paramname(), returns(), flag(), body(), optionalValue())
                .transform(of(END, COMMENT), Syntax.MethodS::transform);
    }

    static Parser<Syntax.MethodS> method() {
        return of(METHOD)
                .then(name(), refid(), param(), paramname(), returns(), flag(), body(), optionalValue())
                .transform(of(END, COMMENT), Syntax.MethodS::transform);
    }

    static Parser<Syntax> optionalValue() {
        return of(OPTIONAL).then(valueLiteral());
    }

    static Parser<Syntax.NameS> name() {
        return of(Syntax.NameS::of, NAME, STRING_LITERAL);
    }

    static Parser<Syntax> trait() {
        return of(TRAIT)
                .then(constT()
                       .or(slotT())
                       .or(methodT())
                       .or(classT())
                       .or(getterT())
                       .or(setterT())
                );
    }

    static Parser<Syntax> constT() {
        return of(CONST).then(qName())
                .optional(SLOT_ID, INT_LITERAL)
                .optional(of(TYPE).then(type()))
                .optional(value())
                .then(END);
    }

    static Parser<Syntax> slotT() {
        return of(SLOT).then(qName())
                .optional(SLOT_ID, INT_LITERAL)
                .optional(of(TYPE).then(type()))
                .optional(value())
                .then(END);
    }

    static Parser<Syntax> methodT() {
        return of(METHOD).then(qName())
                .optional(flag())
                .optional(dispid())
                .then(method().or(of(HASH_INCLUDE, STRING_LITERAL)))
                .then(END, COMMENT);
    }

    static Parser<Syntax> getterT() {
        return of(GETTER).then(qName())
                .optional(flag())
                .optional(dispid())
                .then(method())
                .then(END, COMMENT);
    }

    static Parser<Syntax> setterT() {
        return of(SETTER).then(qName())
                .optional(flag())
                .optional(dispid())
                .then(method())
                .then(END, COMMENT);
    }

    static Parser<Syntax> classT() {
        return of(CLASS).then(qName())
                .optional(of(SLOT_ID, INT_LITERAL))
                .then(HASH_INCLUDE, STRING_LITERAL)
                .then(END, COMMENT);
    }

    static Parser<Syntax> value() {
        return of(VALUE).then(valueLiteral());
    }

    static Parser<Syntax> valueLiteral() {
        return oneOf(
                valueDouble(),
                valueInteger(),
                valueBoolean(),
                valueNull(),
                valueUtf8(),
                valueNamespace()
        );
    }

    static Parser<Syntax> valueNull() {
        return of(VALUE_NULL, OPEN_P, CLOSE_P);
    }

    static Parser<Syntax> valueBoolean() {
        return oneOf(VALUE_TRUE, VALUE_FALSE).then(of(OPEN_P, CLOSE_P));
    }

    static Parser<Syntax> valueInteger() {
        return of(VALUE_INTEGER, OPEN_P)
                .then(of(INT_LITERAL))
                .then(of(CLOSE_P));
    }

    static Parser<Syntax> valueDouble() {
        return of(VALUE_DOUBLE, OPEN_P)
                .then(oneOf(FLOAT_LITERAL, INT_LITERAL, NAN_LITERAL))
                .then(of(CLOSE_P));
    }

    static Parser<Syntax> valueUtf8() {
        return of(VALUE_UTF8, OPEN_P, STRING_LITERAL, CLOSE_P);
    }

    static Parser<Syntax> valueNamespace() {
        return of(NAMESPACE, OPEN_P).then(namespace()).then(CLOSE_P);
    }

    static Parser<Syntax> type() {
        return qName()
                .or(of(TYPENAME, OPEN_P)
                        .then(qName())
                        .then(of(OPEN_T))
                        .then(qName())
                        .then(of(CLOSE_T, CLOSE_P))
                );
    }

    static Parser<Syntax> multiname() {
        return qName()
                .or(rtqName())
                .or(of(MULTINAME, OPEN_P, STRING_LITERAL, COMMA, OPEN_B)
                        .then(more(namespace(), of(COMMA)))
                        .then(of(CLOSE_B, CLOSE_P))
                )
                .or(of(MULTINAME_L, OPEN_P, OPEN_B)
                        .then(more(namespace(), of(COMMA)))
                        .then(of(CLOSE_B, CLOSE_P))
                )
                .or(of(MULTINAME_A, OPEN_P, STRING_LITERAL, COMMA, OPEN_B)
                        .then(more(namespace(), of(COMMA)))
                        .then(of(CLOSE_B, CLOSE_P))
                );
    }

    static Parser<Syntax> body() {
        return of(BODY)
                .then(of(Syntax.MaxStackS::of, MAX_STACK, INT_LITERAL),
                        of(Syntax.LocalCountS::of, LOCAL_COUNT, INT_LITERAL),
                        of(Syntax.InitScopeDepthS::of, INIT_SCOPE_DEPTH, INT_LITERAL),
                        of(Syntax.MaxScopeDepthS::of, MAX_SCOPE_DEPTH, INT_LITERAL),
                        code(),
                        tryFromTo(),
                        of(TRAIT).then(slotT().or(constT())))
                .then(of(END, COMMENT));
    }

    static Parser<Syntax> tryFromTo() {
        return of(TRY, FROM, JUMP_LABEL, TO, JUMP_LABEL, TARGET, JUMP_LABEL, TYPE)
                .then(qName()).then(of(NAME)).then(qName())
                .then(of(END));
    }

    static Parser<Syntax> paramname() {
        return of(PARAM_NAME).then(of(STRING_LITERAL).or(of(NULL_LITERAL)));
    }

    static Parser<Syntax> param() {
        return of(PARAM).then(type().or(of(NULL_LITERAL)));
    }

    static Parser<Syntax> returns() {
        return of(RETURNS).then(type());
    }

    static Parser<Syntax> code() {
        return of(CODE)
                .then(more(instructions()))
                .then(of(END, COMMENT));
    }

    static Parser<Syntax> instructions() {
        return of(DEBUG_FILE, STRING_LITERAL)
                .or(of(DEBUG_LINE, INT_LITERAL))
                .or(of(DEBUG, INT_LITERAL, COMMA, STRING_LITERAL, COMMA, INT_LITERAL, COMMA, INT_LITERAL))
                .or(of(GET_LOCAL_0))
                .or(of(GET_LOCAL_1))
                .or(of(GET_LOCAL_2))
                .or(of(GET_LOCAL_3))
                .or(of(GET_LOCAL, INT_LITERAL))
                .or(of(SET_LOCAL_0))
                .or(of(SET_LOCAL_1))
                .or(of(SET_LOCAL_2))
                .or(of(SET_LOCAL_3))
                .or(of(SET_LOCAL, INT_LITERAL))
                .or(of(INC_LOCAL_I, INT_LITERAL))
                .or(of(GET_SLOT, INT_LITERAL))
                .or(of(SET_SLOT, INT_LITERAL))
                .or(of(APPLY_TYPE, INT_LITERAL))
                .or(of(CONSTRUCT, INT_LITERAL))
                .or(of(CONSTRUCT_SUPER, INT_LITERAL))
                .or(of(CONSTRUCT_PROP).then(qName()).then(of(COMMA, INT_LITERAL)))
                .or(of(BLANK_LINE))
                .or(of(DUP))
                .or(of(KILL, INT_LITERAL))
                .or(of(ADD))
                .or(of(SUBTRACT))
                .or(of(MULTIPLY))
                .or(of(DIVIDE))
                .or(of(MODULO))
                .or(of(NEGATE))
                .or(of(L_SHIFT))
                .or(of(R_SHIFT))
                .or(of(BIT_AND))
                .or(of(BIT_OR))
                .or(of(NOT))
                .or(of(IN))
                .or(of(EQUALS))
                .or(of(GREATER_THAN))
                .or(of(GREATER_EQUALS))
                .or(of(LESS_THAN))
                .or(of(LESS_EQUALS))
                .or(of(TYPEOF))
                .or(of(IS_TYPE_LATE))
                .or(of(INCREMENT))
                .or(of(INCREMENT_I))
                .or(of(DECREMENT))
                .or(of(DECREMENT_I))
                .or(of(COERCE_A))
                .or(of(COERCE_S))
                .or(of(COERCE).then(type()))
                .or(of(CONVERT_B))
                .or(of(CONVERT_D))
                .or(of(CONVERT_I))
                .or(of(CONVERT_S))
                .or(of(CONVERT_U))
                .or(of(CHECKFILTER))
                .or(of(NEXT_NAME))
                .or(of(NEXT_VALUE))
                .or(of(HAS_NEXT_2, INT_LITERAL, COMMA, INT_LITERAL))
                .or(of(SWAP))
                .or(of(IFTRUE, JUMP_LABEL))
                .or(of(IFFALSE, JUMP_LABEL))
                .or(of(IFEQ, JUMP_LABEL))
                .or(of(IFNE, JUMP_LABEL))
                .or(of(IFGT, JUMP_LABEL))
                .or(of(IFGE, JUMP_LABEL))
                .or(of(IFNGT, JUMP_LABEL))
                .or(of(IFNGE, JUMP_LABEL))
                .or(of(IFLT, JUMP_LABEL))
                .or(of(IFLE, JUMP_LABEL))
                .or(of(IFNLT, JUMP_LABEL))
                .or(of(IFNLE, JUMP_LABEL))
                .or(of(IFSTRICTEQ, JUMP_LABEL))
                .or(of(IFSTRICTNE, JUMP_LABEL))
                .or(of(JUMP, JUMP_LABEL))
                .or(of(JUMP_LABEL, LABEL_COLON))
                .or(of(LABEL))
                .or(of(LOOKUP_SWITCH, JUMP_LABEL, COMMA, OPEN_B).then(more(of(JUMP_LABEL), of(COMMA))).then(CLOSE_B))
                .or(of(AS_TYPE_LATE))
                .or(of(COMMENT))
                .or(of(PUSH_SCOPE))
                .or(of(PUSH_WITH))
                .or(of(PUSH_NAN))
                .or(of(PUSH_NULL))
                .or(of(PUSH_TRUE))
                .or(of(PUSH_FALSE))
                .or(of(PUSH_STRING, STRING_LITERAL))
                .or(of(PUSH_BYTE, INT_LITERAL))
                .or(of(PUSH_SHORT, INT_LITERAL))
                .or(of(PUSH_INT, INT_LITERAL))
                .or(of(PUSH_DOUBLE).then(oneOf(FLOAT_LITERAL, INT_LITERAL, NAN_LITERAL)))
                .or(of(PUSH, UNDEFINED_LITERAL))
                .or(of(POP_SCOPE))
                .or(of(POP))
                .or(of(RETURN_VALUE))
                .or(of(RETURN_VOID))
                .or(of(GET_GLOBAL_SCOPE))
                .or(of(GET_DESCENDANTS).then(multiname()))
                .or(of(GET_PROPERTY).then(multiname()))
                .or(of(SET_PROPERTY).then(multiname()))
                .or(of(FIND_PROPERTY).then(multiname()))
                .or(of(FIND_PROP_STRICT).then(multiname()))
                .or(of(DELETE_PROPERTY).then(multiname()))
                .or(of(CALL_PROPERTY).then(multiname()).then(COMMA, INT_LITERAL))
                .or(of(CALL_PROP_VOID).then(multiname()).then(COMMA, INT_LITERAL))
                .or(of(CALL_PROP_LEX).then(multiname()).then(COMMA, INT_LITERAL))
                .or(of(CALL_SUPER).then(multiname()).then(COMMA, INT_LITERAL))
                .or(of(CALL_SUPER_VOID).then(multiname()).then(COMMA, INT_LITERAL))
                .or(of(CALL, INT_LITERAL))
                .or(of(INIT_PROPERTY).then(multiname()))
                .or(of(GET_LEX).then(multiname()))
                .or(of(GET_SCOPE_OBJECT, INT_LITERAL))
                .or(of(NEW_ARRAY, INT_LITERAL))
                .or(of(NEW_OBJECT, INT_LITERAL))
                .or(of(NEW_FUNCTION, STRING_LITERAL))
                .or(of(NEW_CLASS, STRING_LITERAL))
                .or(of(NEW_CATCH, INT_LITERAL))
                .or(of(NEW_ACTIVATION))
                .or(of(THROW))
                ;
    }
}
