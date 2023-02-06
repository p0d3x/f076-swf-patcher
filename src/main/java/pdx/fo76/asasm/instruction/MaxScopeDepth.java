package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class MaxScopeDepth extends Indented1ParamNode<Integer> {
    public MaxScopeDepth(Integer param) {
        super("maxscopedepth", param);
    }
}
