package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
public class End extends Node {

    @Getter
    private final String name;
    String comment;

    public End(String comment) {
        name = "end";
        this.comment = comment;
    }

    @Override
    public String toString() {
        return getName() + " " + comment;
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(StringUtils.repeat(" ", indent - 1) + this), super.stream(indent));
    }
}
