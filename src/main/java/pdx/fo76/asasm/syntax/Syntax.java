package pdx.fo76.asasm.syntax;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pdx.fo76.asasm.token.ParsedToken;

import java.util.List;

public interface Syntax {

    int getLength();

    static Syntax of(List<ParsedToken> tokens) {
        return new SimpleSyntax(tokens);
    }

    static Syntax multi(List<Syntax> rest) {
        return multi(rest, 0);
    }

    static Syntax multi(List<Syntax> rest, int pad) {
        return new CompoundSyntax(rest, pad);
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor
    class SimpleSyntax implements Syntax {
        private final List<ParsedToken> tokens;

        @Override
        public int getLength() {
            return tokens.size();
        }
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor
    class CompoundSyntax implements Syntax {
        private final List<Syntax> rest;
        private final int pad;

        @Override
        public int getLength() {
            return rest.stream().mapToInt(Syntax::getLength).sum() + pad;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class ClassS extends CompoundSyntax {

        public ClassS(List<Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static ClassS transform(Syntax syntax, int pad) {
            return new ClassS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class ScriptS extends CompoundSyntax {

        public ScriptS(List<Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static ScriptS transform(Syntax syntax, int pad) {
            return new ScriptS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class MethodS extends CompoundSyntax {

        public MethodS(List<Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static MethodS transform(Syntax syntax, int pad) {
            return new MethodS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class QNameS extends CompoundSyntax {

        public QNameS(List<Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static QNameS transform(Syntax syntax, int pad) {

            return new QNameS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class NamespaceS extends SimpleSyntax {

        @Getter
        private final String name;
        @Getter
        private final String qualifier;

        public NamespaceS(String name, String qualifier, List<ParsedToken> rest) {
            super(rest);
            this.name = name;
            this.qualifier = qualifier;
        }

        public static NamespaceS of(List<ParsedToken> tokens) {
            var ns = tokens.get(0).getValue();
            var qualifier = tokens.get(2).getValue();
            return new NamespaceS(ns, qualifier, tokens);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class SingleParamSyntax<T> extends SimpleSyntax {

        @Getter
        private final T value;

        public SingleParamSyntax(List<ParsedToken> rest, T param) {
            super(rest);
            this.value = param;
        }

    }

    @EqualsAndHashCode(callSuper = true)
    class RefIdS extends SingleParamSyntax<String> {
        public RefIdS(List<ParsedToken> rest) {
            super(rest, rest.get(1).getValue());
        }

        public static RefIdS of(List<ParsedToken> tokens) {
            return new RefIdS(tokens);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class DispIdS extends SingleParamSyntax<String> {
        public DispIdS(List<ParsedToken> rest) {
            super(rest, rest.get(1).getValue());
        }

        public static DispIdS of(List<ParsedToken> tokens) {
            return new DispIdS(tokens);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class NameS extends SingleParamSyntax<String> {
        public NameS(List<ParsedToken> rest) {
            super(rest, rest.get(1).getValue());
        }

        public static NameS of(List<ParsedToken> tokens) {
            return new NameS(tokens);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class RTQNameS extends SingleParamSyntax<String> {
        public RTQNameS(List<ParsedToken> rest) {
            super(rest, rest.get(2).getValue());
        }

        public static RTQNameS of(List<ParsedToken> tokens) {
            return new RTQNameS(tokens);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class FlagS extends SingleParamSyntax<String> {
        public FlagS(List<ParsedToken> rest) {
            super(rest, rest.get(1).getValue());
        }

        public static FlagS of(List<ParsedToken> tokens) {
            return new FlagS(tokens);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class MaxStackS extends SingleParamSyntax<String> {
        public MaxStackS(List<ParsedToken> rest) {
            super(rest, rest.get(1).getValue());
        }

        public static MaxStackS of(List<ParsedToken> tokens) {
            return new MaxStackS(tokens);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class LocalCountS extends SingleParamSyntax<String> {
        public LocalCountS(List<ParsedToken> rest) {
            super(rest, rest.get(1).getValue());
        }

        public static LocalCountS of(List<ParsedToken> tokens) {
            return new LocalCountS(tokens);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class InitScopeDepthS extends SingleParamSyntax<String> {
        public InitScopeDepthS(List<ParsedToken> rest) {
            super(rest, rest.get(1).getValue());
        }

        public static InitScopeDepthS of(List<ParsedToken> tokens) {
            return new InitScopeDepthS(tokens);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class MaxScopeDepthS extends SingleParamSyntax<String> {
        public MaxScopeDepthS(List<ParsedToken> rest) {
            super(rest, rest.get(1).getValue());
        }

        public static MaxScopeDepthS of(List<ParsedToken> tokens) {
            return new MaxScopeDepthS(tokens);
        }
    }

}
