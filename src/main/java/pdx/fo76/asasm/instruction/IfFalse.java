package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IfFalse extends Indented1ParamNode<LineLabel> {
    public IfFalse(LineLabel param) {
        super("iffalse", param, 20);
    }
}
