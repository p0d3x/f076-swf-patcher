package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PushNull extends IndentedSimpleNode {
    public PushNull() {
        super("pushnull");
    }
}
