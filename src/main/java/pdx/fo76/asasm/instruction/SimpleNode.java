package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class SimpleNode extends Node {

    @Getter
    private final String name;

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(toString()), super.stream(indent));
    }
}
