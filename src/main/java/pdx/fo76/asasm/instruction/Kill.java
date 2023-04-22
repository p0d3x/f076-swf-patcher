package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class Kill extends Indented1ParamNode<Integer> {
    public Kill(Integer param) {
        super(SyntaxConstants.KILL, param, 20);
    }
}
