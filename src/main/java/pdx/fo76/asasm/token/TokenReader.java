package pdx.fo76.asasm.token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TokenReader {

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("([^\\s]+)?(\\s)+");

    public List<ParsedToken> tokenize(List<String> lines) {
        var result = new ArrayList<ParsedToken>();

        for (int i = 0; i < lines.size();i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                result.add(new ParsedToken(Token.BLANK_LINE, null, i, indexOfNonWhitespace(line)));
                continue;
            }

            var pos = 0;
            while (!line.isBlank()) {
                pos += indexOfNonWhitespace(line);
                line = line.strip();
                var found = false;
                for (Token token : Token.values()) {
                    var parsed = token.read(line, i, pos);
                    if (parsed != null) {
                        result.add(parsed);
                        var consumedLength = parsed.getValue().length();
                        pos += consumedLength;
                        line = line.substring(consumedLength);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                     throw new RuntimeException("failed to parse: " + line);
                }
            }
        }

        return result;
    }

    private int indexOfNonWhitespace(String str) {
        var matcher = WHITESPACE_PATTERN.matcher(str);
        if (!matcher.find()) {
            return 0;
        }
        return matcher.end();
    }

}
