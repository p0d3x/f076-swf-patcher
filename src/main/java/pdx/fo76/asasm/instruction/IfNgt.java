package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class IfNgt extends Indented1ParamNode<LineLabel> {
    public IfNgt(LineLabel param) {
        super(SyntaxConstants.IFNGT, param, 20);
    }
}
