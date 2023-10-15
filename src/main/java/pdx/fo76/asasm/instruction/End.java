package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import pdx.fo76.asasm.SyntaxConstants;

import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
public class End extends Node {
    String comment;

    public End(String comment) {
        this.comment = comment;
    }

    @Override
    public String getName() {
        return SyntaxConstants.END;
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
