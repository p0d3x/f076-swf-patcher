package pdx.fo76.asasm.syntax;

import pdx.fo76.asasm.token.ParsedToken;
import pdx.fo76.asasm.token.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pdx.fo76.asasm.token.Token.*;

public interface Parser<T extends Syntax> {

    static Parser<Syntax.ScriptS> script() {
        return of(SCRIPT)
                .atLeastOnce(sinit(), trait())
                .transform(of(END, COMMENT), Syntax.ScriptS::of);
    }

    static Parser<Syntax.ClassS> clazz() {
        return of(Syntax.ClassS::of, peek(CLASS), multi(refid(), instance(), cinit(), trait()).skip(END, COMMENT));
    }
    
    static Parser<Syntax.RefIdS> refid() {
        return exact(Syntax.RefIdS::of, List.of(REFID, STRING_LITERAL));
    }

    static Parser<Syntax.DispIdS> dispid() {
        return exact(Syntax.DispIdS::of, List.of(DISP_ID, INT_LITERAL));
    }
    
    static Parser<Syntax.InstanceS> instance() {
        return of(Syntax.InstanceS::of, peek(INSTANCE),
                qName().atLeastOnce(extend(), implement(), flag(), protectedns(), iinit(), trait()).skip(END, COMMENT));
    }
    
    static Parser<Syntax.QNameS> qName() {
        return of(Syntax.QNameS::of, peek(QNAME, OPEN_P), namespace().thenExact(COMMA, STRING_LITERAL, CLOSE_P));
    }

    static Parser<Syntax.RTQNameS> rtqName() {
        return exact(Syntax.RTQNameS::of, List.of(RTQNAME, OPEN_P, STRING_LITERAL, CLOSE_P));
    }
    
    static Parser<Syntax.NamespaceS> namespace() {
        return ofMultiThenExact(Syntax.NamespaceS::of,
                List.of(NAMESPACE,
                        NS_PRIVATE,
                        NS_PACKAGE,
                        NS_PROTECTED,
                        NS_PACKAGE_INTERNAL,
                        NS_STATIC_PROTECTED),
                List.of(OPEN_P, STRING_LITERAL, CLOSE_P)
        );
    }

    static Parser<Syntax.ExtendsS> extend() {
        return of(Syntax.ExtendsS::of, peek(EXTENDS), qName());
    }

    static Parser<Syntax.ImplementsS> implement() {
        return of(Syntax.ImplementsS::of, peek(IMPLEMENTS), multiname());
    }
    
    static Parser<Syntax.FlagS> flag() {
        return ofSimple(Syntax.FlagS::of, peek(FLAG), of(CONSTANT));
    }
    
    static Parser<Syntax.ProtectedNsS> protectedns() {
        return of(Syntax.ProtectedNsS::of, peek(PROTECTEDNS), namespace());
    }
    
    static Parser<Syntax.MethodS> iinit() {
        return of(IINIT)
                .atLeastOnce(name(), refid(), param(), paramname(), returns(), flag(), body(), optionalValue())
                .transform(of(END, COMMENT), Syntax.MethodS::of);
    }

    static Parser<Syntax.MethodS> cinit() {
        return of(CINIT)
                .atLeastOnce(name(), refid(), param(), paramname(), returns(), flag(), body(), optionalValue())
                .transform(of(END, COMMENT), Syntax.MethodS::of);
    }

    static Parser<Syntax.MethodS> sinit() {
        return of(SINIT)
                .atLeastOnce(name(), refid(), param(), paramname(), returns(), flag(), body(), optionalValue())
                .transform(of(END, COMMENT), Syntax.MethodS::of);
    }

    static Parser<Syntax.MethodS> method() {
        return of(METHOD)
                .atLeastOnce(name(), refid(), param(), paramname(), returns(), flag(), body(), optionalValue())
                .transform(of(END, COMMENT), Syntax.MethodS::of);
    }

    static Parser<Syntax> optionalValue() {
        return of(OPTIONAL).thenExact(valueLiteral());
    }

    static Parser<Syntax.NameS> name() {
        return exact(Syntax.NameS::of, NAME, STRING_LITERAL);
    }

    static Parser<Syntax> trait() {
        return of(TRAIT)
                .thenExact(constT()
                       .or(slotT())
                       .or(methodT())
                       .or(classT())
                       .or(getterT())
                       .or(setterT())
                );
    }

    static Parser<Syntax> constT() {
        return of(CONST).thenExact(qName())
                .optional(SLOT_ID, INT_LITERAL)
                .optional(of(TYPE).thenExact(type()))
                .optional(value())
                .thenExact(END);
    }

    static Parser<Syntax> slotT() {
        return of(SLOT).thenExact(qName())
                .optional(SLOT_ID, INT_LITERAL)
                .optional(of(TYPE).thenExact(type()))
                .optional(value())
                .thenExact(END);
    }

    static Parser<Syntax> methodT() {
        return of(METHOD).thenExact(qName())
                .optional(flag())
                .optional(dispid())
                .thenExact(method().or(of(HASH_INCLUDE, STRING_LITERAL)))
                .thenExact(END, COMMENT);
    }

    static Parser<Syntax> getterT() {
        return of(GETTER).thenExact(qName())
                .optional(flag())
                .optional(dispid())
                .thenExact(method())
                .thenExact(END, COMMENT);
    }

    static Parser<Syntax> setterT() {
        return of(SETTER).thenExact(qName())
                .optional(flag())
                .optional(dispid())
                .thenExact(method())
                .thenExact(END, COMMENT);
    }

    static Parser<Syntax> classT() {
        return of(CLASS).thenExact(qName())
                .optional(of(SLOT_ID, INT_LITERAL))
                .thenExact(HASH_INCLUDE, STRING_LITERAL)
                .thenExact(END, COMMENT);
    }

    static Parser<Syntax> value() {
        return of(VALUE).thenExact(valueLiteral());
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
        return oneOf(VALUE_TRUE, VALUE_FALSE).thenExact(OPEN_P, CLOSE_P);
    }

    static Parser<Syntax> valueInteger() {
        return of(VALUE_INTEGER, OPEN_P, INT_LITERAL, CLOSE_P);
    }

    static Parser<Syntax> valueDouble() {
        return of(VALUE_DOUBLE, OPEN_P)
                .thenExact(oneOf(FLOAT_LITERAL, INT_LITERAL, NAN_LITERAL))
                .thenExact(of(CLOSE_P));
    }

    static Parser<Syntax> valueUtf8() {
        return of(VALUE_UTF8, OPEN_P, STRING_LITERAL, CLOSE_P);
    }

    static Parser<Syntax> valueNamespace() {
        return of(NAMESPACE, OPEN_P).thenExact(namespace()).thenExact(CLOSE_P);
    }

    static Parser<Syntax> type() {
        return qName()
                .or(of(TYPENAME, OPEN_P)
                        .thenExact(qName())
                        .thenExact(of(OPEN_T))
                        .thenExact(qName())
                        .thenExact(CLOSE_T, CLOSE_P)
                );
    }

    static Parser<Syntax> multiname() {
        return qName()
                .or(rtqName())
                .or(of(MULTINAME, OPEN_P, STRING_LITERAL, COMMA, OPEN_B)
                        .thenExact(more(namespace(), of(COMMA)))
                        .thenExact(CLOSE_B, CLOSE_P)
                )
                .or(of(MULTINAME_L, OPEN_P, OPEN_B)
                        .thenExact(more(namespace(), of(COMMA)))
                        .thenExact(CLOSE_B, CLOSE_P)
                )
                .or(of(MULTINAME_A, OPEN_P, STRING_LITERAL, COMMA, OPEN_B)
                        .thenExact(more(namespace(), of(COMMA)))
                        .thenExact(CLOSE_B, CLOSE_P)
                );
    }

    static Parser<Syntax> body() {
        return of(BODY)
                .atLeastOnce(exact(Syntax.MaxStackS::of, MAX_STACK, INT_LITERAL),
                        exact(Syntax.LocalCountS::of, LOCAL_COUNT, INT_LITERAL),
                        exact(Syntax.InitScopeDepthS::of, INIT_SCOPE_DEPTH, INT_LITERAL),
                        exact(Syntax.MaxScopeDepthS::of, MAX_SCOPE_DEPTH, INT_LITERAL),
                        code(),
                        tryFromTo(),
                        of(TRAIT).thenExact(slotT().or(constT())))
                .thenExact(END, COMMENT);
    }

    static Parser<Syntax> tryFromTo() {
        return of(TRY, FROM, JUMP_LABEL, TO, JUMP_LABEL, TARGET, JUMP_LABEL, TYPE)
                .thenExact(qName()).thenExact(of(NAME)).thenExact(qName())
                .thenExact(END);
    }

    static Parser<Syntax> paramname() {
        return of(PARAM_NAME).thenExact(of(STRING_LITERAL).or(of(NULL_LITERAL)));
    }

    static Parser<Syntax> param() {
        return of(PARAM).thenExact(type().or(of(NULL_LITERAL)));
    }

    static Parser<Syntax> returns() {
        return of(RETURNS).thenExact(type());
    }

    static Parser<Syntax> code() {
        return of(CODE).thenExact(more(instructions())).thenExact(END, COMMENT);
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
                .or(of(CONSTRUCT_PROP).thenExact(qName()).thenExact(COMMA, INT_LITERAL))
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
                .or(of(COERCE).thenExact(type()))
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
                .or(of(LOOKUP_SWITCH, JUMP_LABEL, COMMA, OPEN_B).thenExact(more(of(JUMP_LABEL), of(COMMA))).thenExact(CLOSE_B))
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
                .or(of(PUSH_DOUBLE).thenExact(oneOf(FLOAT_LITERAL, INT_LITERAL, NAN_LITERAL)))
                .or(of(PUSH, UNDEFINED_LITERAL))
                .or(of(POP_SCOPE))
                .or(of(POP))
                .or(of(RETURN_VALUE))
                .or(of(RETURN_VOID))
                .or(of(GET_GLOBAL_SCOPE))
                .or(of(GET_DESCENDANTS).thenExact(multiname()))
                .or(of(GET_PROPERTY).thenExact(multiname()))
                .or(of(SET_PROPERTY).thenExact(multiname()))
                .or(of(FIND_PROPERTY).thenExact(multiname()))
                .or(of(FIND_PROP_STRICT).thenExact(multiname()))
                .or(of(DELETE_PROPERTY).thenExact(multiname()))
                .or(of(CALL_PROPERTY).thenExact(multiname()).thenExact(COMMA, INT_LITERAL))
                .or(of(CALL_PROP_VOID).thenExact(multiname()).thenExact(COMMA, INT_LITERAL))
                .or(of(CALL_PROP_LEX).thenExact(multiname()).thenExact(COMMA, INT_LITERAL))
                .or(of(CALL_SUPER).thenExact(multiname()).thenExact(COMMA, INT_LITERAL))
                .or(of(CALL_SUPER_VOID).thenExact(multiname()).thenExact(COMMA, INT_LITERAL))
                .or(of(CALL, INT_LITERAL))
                .or(of(INIT_PROPERTY).thenExact(multiname()))
                .or(of(GET_LEX).thenExact(multiname()))
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

    Either<Syntax, ParseStop> parse(List<ParsedToken> tokens, int pos);

    default Parser<Syntax> skip(Token... sequence) {
        return (tokens, pos) -> {
            var first = parse(tokens, pos);
            if (first.isRight()) {
                return first;
            }
            var peek = peek(sequence);
            if (!peek.matches(tokens, pos + first.left().getLength())) {
                return Either.right(new ParseStop(pos + first.left().getLength()));
            }
            return Either.left(first.left().pad(peek.getLength()));
        };
    }

    default Parser<Syntax> thenExact(Token... options) {
        return atLeastOnce(of(options));
    }

    default Parser<Syntax> atLeastOnce(Parser<? extends Syntax>... options) {
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

    default Parser<Syntax> thenExact(Parser<? extends Syntax> required) {
        return atLeastOnce(required);
    }

    static Parser<Syntax> multi(Parser<? extends Syntax>... options) {
        return (tokens, pos) -> {
            var cur = pos;
            var bestDepth = pos;
            var result = new ArrayList<Syntax>();
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
            return Either.left(Syntax.multi(result));
        };
    }

    default <V extends Syntax> Parser<V> transform(Parser<Syntax> peek, Transformer<Syntax, V> transformer) {
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

    static <T extends Syntax> Parser<T> of(Transformer<Syntax, T> transform, Peek peek, Parser<? extends Syntax> then) {
        return (tokens, pos) -> {
            if (!peek.matches(tokens, pos)) {
                return Either.right(new ParseStop(pos));
            }
            pos += peek.getLength();
            var result = then.parse(tokens, pos);
            if (result.isRight()) {
                return result;
            }
            return Either.left(transform.transform(result.left(), peek.getLength()));
        };
    }

    static Parser<Syntax> of(Token... sequence) {
        return of(Arrays.asList(sequence));
    }

    static <T extends Syntax> Parser<T> exact(Transformer<List<ParsedToken>, T> mapper, Token... sequence) {
        return exact(mapper, Arrays.asList(sequence));
    }

    static Parser<Syntax> of(List<Token> sequence) {
        return ofMultiThenExact(Syntax::of, List.of(), sequence);
    }

    static <T extends Syntax> Parser<T> exact(Transformer<List<ParsedToken>, T> mapper, List<Token> sequence) {
        return ofMultiThenExact(mapper, List.of(), sequence);
    }

    static <T extends Syntax> Parser<T> ofMultiThenExact(Transformer<List<ParsedToken>, T> mapper,
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
            return Either.left(mapper.transform(tokens.subList(pos, pos + offs + sequence.size())));
        };
    }

    static <T extends Syntax> Parser<T> ofSimple(Transformer<List<ParsedToken>, T> transform, Peek peek,
                                                 Parser<? extends Syntax> then) {
        return (tokens, pos) -> {
            if (!peek.matches(tokens, pos)) {
                return Either.right(new ParseStop(pos));
            }
            var start = pos + peek.getLength();
            var result = then.parse(tokens, start);
            if (result.isRight()) {
                return result;
            }
            var len = result.left().getLength();
            return Either.left(transform.transform(tokens.subList(start, start + len), peek.getLength()));
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
                nextParser = separator.atLeastOnce(parser);
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
        return ofMultiThenExact(Syntax::of, Arrays.asList(tokens), List.of());
    }

    static Parser<Syntax> oneOf(Parser<? extends Syntax>... parsers) {
        return (tokens, pos) -> {
            var bestPos = pos;
            for (Parser<? extends Syntax> parser : parsers) {
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

    static Peek peek(Token... sequence) {
        return new Peek.PeekSequence(sequence);
    }

    interface Peek {
        boolean matches(List<ParsedToken> tokens, int pos);
        int getLength();

        record PeekSequence(Token[] sequence) implements Peek {
            @Override
            public boolean matches(List<ParsedToken> tokens, int pos) {
                if (sequence.length > tokens.size() - pos) {
                    return false;
                }
                for (int i = 0; i < sequence.length; i++) {
                    if (sequence[i] != tokens.get(pos + i).getToken()) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public int getLength() {
                return sequence.length;
            }
        }
    }
}
