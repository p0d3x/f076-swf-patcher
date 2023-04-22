package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class GetLocal extends Indented1ParamNode<Integer> {
    public GetLocal(Integer param) {
        super(SyntaxConstants.GET_LOCAL, param, 20);
    }
}
