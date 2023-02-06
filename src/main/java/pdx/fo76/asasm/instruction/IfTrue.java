package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IfTrue extends Indented1ParamNode<LineLabel> {
    public IfTrue(LineLabel param) {
        super("iftrue", param, 20);
    }
}
