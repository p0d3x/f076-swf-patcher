package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IfStrictNe extends Indented1ParamNode<LineLabel> {
    public IfStrictNe(LineLabel param) {
        super("ifstrictne", param, 20);
    }
}
