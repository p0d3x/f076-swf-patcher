package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class GetLocal0 extends IndentedSimpleNode {
    public GetLocal0() {
        super(SyntaxConstants.GET_LOCAL_0);
    }
}
