package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class ReturnValue extends IndentedSimpleNode {
    public ReturnValue() {
        super(SyntaxConstants.RETURN_VALUE);
    }
}
