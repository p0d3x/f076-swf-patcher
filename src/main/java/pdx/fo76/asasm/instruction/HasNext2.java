package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class HasNext2 extends Indented1ParamNode<String> {

    public HasNext2(String param) {
        super(SyntaxConstants.HAS_NEXT_2, param, 20);
    }
}
