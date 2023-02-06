package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IfLt extends Indented1ParamNode<LineLabel> {
    public IfLt(LineLabel param) {
        super("iflt", param, 20);
    }
}
