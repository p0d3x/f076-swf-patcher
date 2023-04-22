package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class SetLocal extends Indented1ParamNode<Integer> {
    public SetLocal(Integer param) {
        super(SyntaxConstants.SET_LOCAL, param, 20);
    }
}
