package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Not extends IndentedSimpleNode {
    public Not() {
        super("not");
    }
}
