package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class Construct extends Indented1ParamNode<Integer> {

    public Construct(Integer param) {
        super(SyntaxConstants.CONSTRUCT, param, 20);
    }
}
