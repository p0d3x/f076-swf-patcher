package pdx.fo76.asasm.token;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static pdx.fo76.asasm.Util.readAll;

@Slf4j
class TokenReaderTest {

    @Test
    void stringLiteral_doubleQuotes() {
        var lines = List.of("      pushstring          \"\\\"\"");
        var tokenReader = new TokenReader();
        var result = tokenReader.tokenize(lines);
        log.info("{} tokens read.", result.size());
    }

    @Test
    void readAllTokens() throws IOException {
        var dir = Path.of("I:\\Modding\\Fallout 76\\NukaShopper\\loader-hudmenu\\generated\\interface\\hudmenu-0");
        readAll(dir, (f, lines) -> {
            var tokenReader = new TokenReader();
            var result = tokenReader.tokenize(lines);
            log.info("{}: {} tokens read.", f.getFileName(), result.size());
        });
    }

}
