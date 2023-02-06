package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IfEq extends Indented1ParamNode<LineLabel> {
    public IfEq(LineLabel param) {
        super("ifeq", param, 20);
    }
}
