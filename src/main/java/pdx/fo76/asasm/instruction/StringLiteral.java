package pdx.fo76.asasm.instruction;

public record StringLiteral(String value) {
    @Override
    public String toString() {
        return value != null ? "\"" + value + "\"" : "null";
    }
}
