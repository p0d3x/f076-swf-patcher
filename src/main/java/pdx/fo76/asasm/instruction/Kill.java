package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Kill extends Indented1ParamNode<Integer> {
    public Kill(Integer param) {
        super("kill", param, 20);
    }
}
