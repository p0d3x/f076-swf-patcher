package pdx.fo76.asasm.instruction;

import java.util.stream.Stream;

public class BlankLine extends Node {

    @Override
    public String toString() {
        return "";
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(toString()), super.stream(indent));
    }

    @Override
    public String getName() {
        return "";
    }
}
