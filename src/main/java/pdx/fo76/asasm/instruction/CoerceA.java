package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class CoerceA extends IndentedSimpleNode {
    public CoerceA() {
        super("coerce_a");
    }
}
