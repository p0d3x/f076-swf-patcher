package pdx.fo76.asasm.token;

import lombok.Value;

@Value
public class ParsedToken {
    Token token;
    String value;
}
