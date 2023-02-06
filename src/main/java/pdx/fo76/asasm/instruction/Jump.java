package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Jump extends Indented1ParamNode<LineLabel> {
    public Jump(LineLabel param) {
        super("jump", param, 20);
    }
}
