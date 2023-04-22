package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class ReturnVoid extends IndentedSimpleNode {
    public ReturnVoid() {
        super(SyntaxConstants.RETURN_VOID);
    }
}
