package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class DebugLine extends Indented1ParamNode<Integer> {

    public DebugLine(Integer param) {
        super("debugline", param, 20);
    }
}
