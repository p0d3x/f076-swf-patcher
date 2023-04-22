package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class PushTrue extends IndentedSimpleNode {
    public PushTrue() {
        super(SyntaxConstants.PUSH_TRUE);
    }
}
