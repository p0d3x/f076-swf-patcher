package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class MaxScopeDepth extends Indented1ParamNode<Integer> {
    public MaxScopeDepth(Integer param) {
        super(SyntaxConstants.MAX_SCOPE_DEPTH, param);
    }
}
