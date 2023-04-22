package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class GetLocal2 extends IndentedSimpleNode {
    public GetLocal2() {
        super(SyntaxConstants.GET_LOCAL_2);
    }
}
