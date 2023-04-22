package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class IfStrictNe extends Indented1ParamNode<LineLabel> {
    public IfStrictNe(LineLabel param) {
        super(SyntaxConstants.IFSTRICTNE, param, 20);
    }
}
