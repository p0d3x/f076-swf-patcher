package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class PushByte extends Indented1ParamNode<Integer> {

    public PushByte(Integer param) {
        super(SyntaxConstants.PUSH_BYTE, param, 20);
    }
}
