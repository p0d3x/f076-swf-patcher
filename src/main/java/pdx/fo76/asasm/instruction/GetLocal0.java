package pdx.fo76.asasm.instruction;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@Slf4j
public class GetLocal0 extends Node {
    @Override
    public String toString() {
        return "getlocal0";
    }

    @Override
    public String getName() {
        return "getlocal0";
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(StringUtils.repeat(" ", indent) + this), super.stream(indent));
    }
}
