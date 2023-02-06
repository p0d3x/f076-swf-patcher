package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PushFalse extends IndentedSimpleNode {
    public PushFalse() {
        super("pushfalse");
    }
}
