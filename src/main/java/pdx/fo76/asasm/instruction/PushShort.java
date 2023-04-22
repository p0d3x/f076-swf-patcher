package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class PushShort extends Indented1ParamNode<Integer> {

    public PushShort(Integer param) {
        super(SyntaxConstants.PUSH_SHORT, param, 20);
    }
}
