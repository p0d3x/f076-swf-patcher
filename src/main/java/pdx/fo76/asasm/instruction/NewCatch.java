package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class NewCatch extends Indented1ParamNode<Integer> {

    public NewCatch(Integer param) {
        super(SyntaxConstants.NEW_CATCH, param, 20);
    }
}
