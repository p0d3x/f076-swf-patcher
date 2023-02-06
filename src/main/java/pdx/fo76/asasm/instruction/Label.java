package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Label extends IndentedSimpleNode {
    public Label() {
        super("label");
    }
}
