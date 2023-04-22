package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class PushInt extends Indented1ParamNode<Integer> {

    public PushInt(Integer param) {
        super(SyntaxConstants.PUSH_INT, param, 20);
    }
}
