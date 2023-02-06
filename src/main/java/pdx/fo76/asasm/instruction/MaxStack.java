package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class MaxStack extends Indented1ParamNode<Integer> {
    public MaxStack(Integer param) {
        super("maxstack", param);
    }
}
