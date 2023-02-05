package pdx.fo76.asasm.instruction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class PushString extends Node {
    String param;

    @Override
    public String toString() {
        return "pushstring          \"" + param + "\"";
    }

    @Override
    public String getName() {
        return "pushstring";
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(StringUtils.repeat(" ", indent) + this), super.stream(indent));
    }

    @Override
    public boolean paramsEquals(String testValue) {
        return param.equals(testValue);
    }
}
