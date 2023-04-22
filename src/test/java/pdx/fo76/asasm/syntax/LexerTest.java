package pdx.fo76.asasm.syntax;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pdx.fo76.asasm.token.TokenReader;

import java.io.IOException;
import java.nio.file.Path;

import static pdx.fo76.asasm.Util.readAll;

@Slf4j
class LexerTest {

    @Test
    void lexAll() throws IOException {
        var dir = Path.of("I:\\Modding\\Fallout 76\\NukaShopper\\loader-hudmenu\\generated\\interface\\hudmenu-0");
        readAll(dir, (f, lines) -> {
            var tokenReader = new TokenReader();
            var tokens = tokenReader.tokenize(lines);
            log.info("{}: {} tokens read.", f.getFileName(), tokens.size());
            var lexer = new Lexer();
            var lexes = lexer.lex(tokens);
        });
    }
}
