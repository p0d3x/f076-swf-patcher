package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Divide extends IndentedSimpleNode {
    public Divide() {
        super("divide");
    }
}
