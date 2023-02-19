package pdx.fo76.asasm.syntax;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pdx.fo76.asasm.token.ParsedToken;

import java.util.List;

public interface Syntax {

    int getLength();

    static Syntax of(List<ParsedToken> tokens) {
        return of(tokens, 0);
    }

    static Syntax of(List<ParsedToken> tokens, int pad) {
        return new SimpleSyntax(tokens, pad);
    }

    static Syntax multi(List<? extends Syntax> rest) {
        return multi(rest, 0);
    }

    static Syntax multi(List<? extends Syntax> rest, int pad) {
        return new CompoundSyntax(rest, pad);
    }

    Syntax pad(int length);

    @EqualsAndHashCode
    @AllArgsConstructor
    class SimpleSyntax implements Syntax {
        private final List<ParsedToken> tokens;
        int pad;

        @Override
        public int getLength() {
            return tokens.size() + pad;
        }

        @Override
        public Syntax pad(int length) {
            pad += length;
            return this;
        }
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    class CompoundSyntax implements Syntax {
        @Getter
        private final List<? extends Syntax> rest;
        int pad;

        @Override
        public int getLength() {
            return rest.stream().mapToInt(Syntax::getLength).sum() + pad;
        }

        @Override
        public Syntax pad(int length) {
            pad += length;
            return this;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class ClassS extends CompoundSyntax {
        private final List<? extends Syntax> contents;

        public ClassS(List<? extends Syntax> rest, int pad) {
            super(rest, pad);
            this.contents = ((CompoundSyntax) rest.get(0)).getRest();
        }

        public static ClassS of(Syntax syntax, int pad) {
            return new ClassS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class ClassBodyS extends CompoundSyntax {

        public ClassBodyS(List<? extends Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static ClassBodyS of(List<? extends Syntax> syntax) {
            return new ClassBodyS(syntax, 0);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class InstanceS extends CompoundSyntax {

        public InstanceS(List<? extends Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static InstanceS of(Syntax syntax, int pad) {
            return new InstanceS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class ScriptS extends CompoundSyntax {

        public ScriptS(List<? extends Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static ScriptS of(Syntax syntax, int pad) {
            return new ScriptS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class MethodS extends CompoundSyntax {

        public MethodS(List<? extends Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static MethodS of(Syntax syntax, int pad) {
            return new MethodS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class MethodBodyS extends CompoundSyntax {

        public MethodBodyS(List<? extends Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static MethodBodyS of(List<? extends Syntax> syntax) {
            return new MethodBodyS(syntax, 0);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class QNameS extends CompoundSyntax {

        public QNameS(List<? extends Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static QNameS of(Syntax syntax, int pad) {
            return new QNameS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class ExtendsS extends CompoundSyntax {

        public ExtendsS(List<Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static ExtendsS of(Syntax syntax, int pad) {
            return new ExtendsS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class ProtectedNsS extends CompoundSyntax {

        public ProtectedNsS(List<Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static ProtectedNsS of(Syntax syntax, int pad) {
            return new ProtectedNsS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class ImplementsS extends CompoundSyntax {

        public ImplementsS(List<Syntax> rest, int pad) {
            super(rest, pad);
        }

        public static ImplementsS of(Syntax syntax, int pad) {
            return new ImplementsS(List.of(syntax), pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class NamespaceS extends SimpleSyntax {

        @Getter
        private final String name;
        @Getter
        private final String qualifier;

        public NamespaceS(String name, String qualifier, List<ParsedToken> rest, int pad) {
            super(rest, pad);
            this.name = name;
            this.qualifier = qualifier;
        }

        public static NamespaceS of(List<ParsedToken> tokens, int pad) {
            var ns = tokens.get(0).getValue();
            var qualifier = tokens.get(2).getValue();
            return new NamespaceS(ns, qualifier, tokens, pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class SingleParamSyntax<T> extends SimpleSyntax {

        @Getter
        protected final T value;

        public SingleParamSyntax(List<ParsedToken> rest, int pad, T param) {
            super(rest, pad);
            this.value = param;
        }

    }

    @EqualsAndHashCode(callSuper = true)
    class RefIdS extends SingleParamSyntax<String> {
        public RefIdS(List<ParsedToken> rest, int pad) {
            super(rest, pad, rest.get(1).getValue());
        }

        public static RefIdS of(List<ParsedToken> tokens, int pad) {
            return new RefIdS(tokens, pad);
        }

        @Override
        public String toString() {
            return "refid " + value;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class DispIdS extends SingleParamSyntax<String> {
        public DispIdS(List<ParsedToken> rest, int pad) {
            super(rest, pad, rest.get(1).getValue());
        }

        public static DispIdS of(List<ParsedToken> tokens, int pad) {
            return new DispIdS(tokens, pad);
        }

        @Override
        public String toString() {
            return "dispid " + value;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class NameS extends SingleParamSyntax<String> {
        public NameS(List<ParsedToken> rest, int pad) {
            super(rest, pad, rest.get(1).getValue());
        }

        public static NameS of(List<ParsedToken> tokens, int pad) {
            return new NameS(tokens, pad);
        }

        @Override
        public String toString() {
            return "name " + value;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class RTQNameS extends SingleParamSyntax<String> {
        public RTQNameS(List<ParsedToken> rest, int pad) {
            super(rest, pad, rest.get(2).getValue());
        }

        public static RTQNameS of(List<ParsedToken> tokens, int pad) {
            return new RTQNameS(tokens, pad);
        }

        @Override
        public String toString() {
            return "RTQName(" + value + ")";
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class FlagS extends SingleParamSyntax<String> {
        public FlagS(List<ParsedToken> rest, int pad) {
            super(rest, pad, rest.get(0).getValue());
        }

        public static FlagS of(List<ParsedToken> tokens, int pad) {
            return new FlagS(tokens, pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class MaxStackS extends SingleParamSyntax<String> {
        public MaxStackS(List<ParsedToken> rest, int pad) {
            super(rest, pad, rest.get(1).getValue());
        }

        public static MaxStackS of(List<ParsedToken> tokens, int pad) {
            return new MaxStackS(tokens, pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class LocalCountS extends SingleParamSyntax<String> {
        public LocalCountS(List<ParsedToken> rest, int pad) {
            super(rest, pad, rest.get(1).getValue());
        }

        public static LocalCountS of(List<ParsedToken> tokens, int pad) {
            return new LocalCountS(tokens, pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class InitScopeDepthS extends SingleParamSyntax<String> {
        public InitScopeDepthS(List<ParsedToken> rest, int pad) {
            super(rest, pad, rest.get(1).getValue());
        }

        public static InitScopeDepthS of(List<ParsedToken> tokens, int pad) {
            return new InitScopeDepthS(tokens, pad);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    class MaxScopeDepthS extends SingleParamSyntax<String> {
        public MaxScopeDepthS(List<ParsedToken> rest, int pad) {
            super(rest, pad, rest.get(1).getValue());
        }

        public static MaxScopeDepthS of(List<ParsedToken> tokens, int pad) {
            return new MaxScopeDepthS(tokens, pad);
        }
    }

}
