package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Pop extends IndentedSimpleNode {
    public Pop() {
        super("pop");
    }
}
