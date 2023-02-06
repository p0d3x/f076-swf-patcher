package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public abstract class IndentedSimpleNode extends Node {

    @Getter
    private final String name;

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(StringUtils.repeat(" ", indent) + this), super.stream(indent));
    }
}
