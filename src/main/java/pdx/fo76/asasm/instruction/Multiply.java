package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Multiply extends IndentedSimpleNode {
    public Multiply() {
        super("multiply");
    }
}
