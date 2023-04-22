package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class IfGt extends Indented1ParamNode<LineLabel> {

    public IfGt(LineLabel param) {
        super(SyntaxConstants.IFGT, param, 20);
    }
}
