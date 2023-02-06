package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class LocalCount extends Indented1ParamNode<Integer> {
    public LocalCount(Integer param) {
        super("localcount", param);
    }
}
