package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class PushDouble extends Indented1ParamNode<String> {
    public PushDouble(String param) {
        super(SyntaxConstants.PUSH_DOUBLE, param, 20);
    }
}
