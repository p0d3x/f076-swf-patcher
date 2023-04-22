package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class InitScopeDepth extends Indented1ParamNode<Integer> {
    public InitScopeDepth(Integer param) {
        super(SyntaxConstants.INIT_SCOPE_DEPTH, param);
    }
}
