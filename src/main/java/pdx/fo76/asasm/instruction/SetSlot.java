package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class SetSlot extends Indented1ParamNode<Integer> {

    public SetSlot(Integer param) {
        super(SyntaxConstants.SET_SLOT, param, 20);
    }
}
