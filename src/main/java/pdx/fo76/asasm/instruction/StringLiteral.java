package pdx.fo76.asasm.instruction;

import lombok.Value;

@Value
public class StringLiteral {
    String value;

    @Override
    public String toString() {
        return value != null ? "\"" + value + "\"" : "null";
    }
}
