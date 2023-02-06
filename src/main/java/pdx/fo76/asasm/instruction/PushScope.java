package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PushScope extends IndentedSimpleNode {
    public PushScope() {
        super("pushscope");
    }
}
