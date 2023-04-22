package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class IfEq extends Indented1ParamNode<LineLabel> {
    public IfEq(LineLabel param) {
        super(SyntaxConstants.IFEQ, param, 20);
    }
}
