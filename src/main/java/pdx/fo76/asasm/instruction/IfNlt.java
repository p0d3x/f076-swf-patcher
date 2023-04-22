package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class IfNlt extends Indented1ParamNode<LineLabel> {
    public IfNlt(LineLabel param) {
        super(SyntaxConstants.IFNLT, param, 20);
    }
}
