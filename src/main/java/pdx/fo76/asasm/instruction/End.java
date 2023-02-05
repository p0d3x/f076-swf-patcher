package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Value
public class End extends Node {
    String comment;

    @Override
    public String toString() {
        return "end " + comment;
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(StringUtils.repeat(" ", indent - 1) + this), super.stream(indent));
    }

    @Override
    public String getName() {
        return "end";
    }
}
