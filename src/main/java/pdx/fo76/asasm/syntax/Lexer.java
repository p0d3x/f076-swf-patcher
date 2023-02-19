package pdx.fo76.asasm.syntax;

import pdx.fo76.asasm.token.ParsedToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Lexer {

    private static final Set<Parser<?>> PARSERS = Set.of(
            Parser.clazz(),
            Parser.script(),
            Parser.method()
    );

    public List<Syntax> lex(List<ParsedToken> tokens) {
        var result = new ArrayList<Syntax>();
        for (int i = 0; i < tokens.size();) {
            var found = false;
            var bestPos = i;
            for (Parser<?> type : PARSERS) {
                var lex = type.parse(tokens, i);
                if (lex.isLeft()) {
                    i += lex.left().getLength();
                    found = true;
                    result.add(lex.left());
                } else {
                    bestPos = Math.max(bestPos, lex.right().getPosition());
                }
            }
            if (!found) {
                throw new RuntimeException("error at " + bestPos + ": " + tokens.get(bestPos));
            }
        }
        return result;
    }

}
