package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class ApplyType extends Indented1ParamNode<Integer> {

    public ApplyType(Integer param) {
        super(SyntaxConstants.APPLY_TYPE, param, 20);
    }
}
