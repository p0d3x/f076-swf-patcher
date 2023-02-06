package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IfNe extends Indented1ParamNode<LineLabel> {
    public IfNe(LineLabel param) {
        super("ifne", param, 20);
    }
}
