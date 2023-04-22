package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class IfNe extends Indented1ParamNode<LineLabel> {
    public IfNe(LineLabel param) {
        super(SyntaxConstants.IFNE, param, 20);
    }
}
