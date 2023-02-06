package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class InitScopeDepth extends Indented1ParamNode<Integer> {
    public InitScopeDepth(Integer param) {
        super("initscopedepth", param);
    }
}
