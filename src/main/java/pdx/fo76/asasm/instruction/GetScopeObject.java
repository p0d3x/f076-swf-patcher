package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class GetScopeObject extends Indented1ParamNode<Integer> {

    public GetScopeObject(Integer param) {
        super(SyntaxConstants.GET_SCOPE_OBJECT, param, 20);
    }
}
