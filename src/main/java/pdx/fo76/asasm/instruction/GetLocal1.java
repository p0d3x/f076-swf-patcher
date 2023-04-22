package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class GetLocal1 extends IndentedSimpleNode {
    public GetLocal1() {
        super(SyntaxConstants.GET_LOCAL_1);
    }
}
