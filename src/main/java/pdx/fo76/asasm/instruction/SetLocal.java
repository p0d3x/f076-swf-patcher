package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class SetLocal extends Indented1ParamNode<Integer> {
    public SetLocal(Integer param) {
        super("setlocal", param, 20);
    }
}
