package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IfNgt extends Indented1ParamNode<LineLabel> {
    public IfNgt(LineLabel param) {
        super("ifngt", param, 20);
    }
}
