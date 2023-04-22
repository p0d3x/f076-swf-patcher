package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class IfFalse extends Indented1ParamNode<LineLabel> {
    public IfFalse(LineLabel param) {
        super(SyntaxConstants.IFFALSE, param, 20);
    }
}
