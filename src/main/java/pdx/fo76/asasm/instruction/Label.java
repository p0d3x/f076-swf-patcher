package pdx.fo76.asasm.instruction;

import lombok.Value;

import java.util.stream.Stream;

@Value
public class Label extends Node {
    String name;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(name), super.stream(indent));
    }
}
