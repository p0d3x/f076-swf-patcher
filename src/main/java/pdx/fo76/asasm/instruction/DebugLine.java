package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class DebugLine extends Indented1ParamNode<Integer> {

    public DebugLine(Integer param) {
        super(SyntaxConstants.DEBUG_LINE, param, 20);
    }
}
