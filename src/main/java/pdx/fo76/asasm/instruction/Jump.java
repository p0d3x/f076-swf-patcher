package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class Jump extends Indented1ParamNode<LineLabel> {
    public Jump(LineLabel param) {
        super(SyntaxConstants.JUMP, param, 20);
    }
}
