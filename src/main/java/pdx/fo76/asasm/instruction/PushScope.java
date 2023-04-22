package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class PushScope extends IndentedSimpleNode {
    public PushScope() {
        super(SyntaxConstants.PUSH_SCOPE);
    }
}
