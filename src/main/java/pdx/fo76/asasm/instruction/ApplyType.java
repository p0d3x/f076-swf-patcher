package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ApplyType extends Indented1ParamNode<Integer> {

    public ApplyType(Integer param) {
        super("applytype", param, 20);
    }
}
