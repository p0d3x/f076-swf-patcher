package pdx.fo76.asasm.token;

import java.util.ArrayList;
import java.util.List;

public class TokenReader {

    public List<ParsedToken> tokenize(List<String> lines) {
        var result = new ArrayList<ParsedToken>();

        for (int i = 0; i < lines.size();i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                result.add(new ParsedToken(Token.BLANK_LINE, null));
                continue;
            }

            while (!line.isBlank()) {
                line = line.strip();
                var found = false;
                for (Token token : Token.values()) {
                    var parsed = token.read(line);
                    if (parsed != null) {
                        result.add(parsed);
                        line = line.substring(parsed.getValue().length());
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

}
