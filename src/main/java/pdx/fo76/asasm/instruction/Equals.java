package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Equals extends IndentedSimpleNode {
    public Equals() {
        super("equals");
    }
}
