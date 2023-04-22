package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class GetSlot extends Indented1ParamNode<Integer> {

    public GetSlot(Integer param) {
        super(SyntaxConstants.GET_SLOT, param, 20);
    }
}
