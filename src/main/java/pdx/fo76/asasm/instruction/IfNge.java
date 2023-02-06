package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IfNge extends Indented1ParamNode<LineLabel> {
    public IfNge(LineLabel param) {
        super("ifnge", param, 20);
    }
}
