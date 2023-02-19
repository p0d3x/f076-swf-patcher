package pdx.fo76.asasm.token;

public interface Tokenizer {
    ParsedToken read(Token token, String line, int lineNumber, int linePos);
}
