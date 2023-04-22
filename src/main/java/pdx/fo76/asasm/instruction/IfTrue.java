package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class IfTrue extends Indented1ParamNode<LineLabel> {
    public IfTrue(LineLabel param) {
        super(SyntaxConstants.IFTRUE, param, 20);
    }
}
