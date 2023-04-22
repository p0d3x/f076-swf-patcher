package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class IfLt extends Indented1ParamNode<LineLabel> {
    public IfLt(LineLabel param) {
        super(SyntaxConstants.IFLT, param, 20);
    }
}
