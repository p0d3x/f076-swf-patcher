package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class GetScopeObject extends Indented1ParamNode<Integer> {

    public GetScopeObject(Integer param) {
        super("getscopeobject", param, 20);
    }
}
