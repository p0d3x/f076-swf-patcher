package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class MaxStack extends Indented1ParamNode<Integer> {
    public MaxStack(Integer param) {
        super(SyntaxConstants.MAX_STACK, param);
    }
}
