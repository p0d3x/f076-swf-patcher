package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class GetLocal3 extends IndentedSimpleNode {
    public GetLocal3() {
        super(SyntaxConstants.GET_LOCAL_3);
    }
}
