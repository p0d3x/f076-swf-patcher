package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class IfNge extends Indented1ParamNode<LineLabel> {
    public IfNge(LineLabel param) {
        super(SyntaxConstants.IFNGE, param, 20);
    }
}
