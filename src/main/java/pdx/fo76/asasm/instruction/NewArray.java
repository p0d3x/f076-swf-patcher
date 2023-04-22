package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class NewArray extends Indented1ParamNode<Integer> {

    public NewArray(Integer param) {
        super(SyntaxConstants.NEW_ARRAY, param, 20);
    }
}
