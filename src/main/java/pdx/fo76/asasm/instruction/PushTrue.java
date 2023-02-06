package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PushTrue extends IndentedSimpleNode {
    public PushTrue() {
        super("pushtrue");
    }
}
