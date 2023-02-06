package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IfNlt extends Indented1ParamNode<LineLabel> {
    public IfNlt(LineLabel param) {
        super("ifnlt", param, 20);
    }
}
