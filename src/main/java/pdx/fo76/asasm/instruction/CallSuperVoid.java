package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class CallSuperVoid extends Indented1ParamNode<String> {

    public CallSuperVoid(String param) {
        super(SyntaxConstants.CALL_SUPER_VOID, param, 20);
    }
}
