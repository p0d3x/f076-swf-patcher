package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class PushFalse extends IndentedSimpleNode {
    public PushFalse() {
        super(SyntaxConstants.PUSH_FALSE);
    }
}
