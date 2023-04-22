package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class LocalCount extends Indented1ParamNode<Integer> {
    public LocalCount(Integer param) {
        super(SyntaxConstants.LOCAL_COUNT, param);
    }
}
