package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class ConstructSuper extends Indented1ParamNode<Integer> {

    public ConstructSuper(Integer param) {
        super(SyntaxConstants.CONSTRUCT_SUPER, param, 20);
    }
}
