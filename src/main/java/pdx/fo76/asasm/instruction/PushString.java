package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class PushString extends Indented1ParamNode<StringLiteral> {

    public PushString(StringLiteral param) {
        super(SyntaxConstants.PUSH_STRING, param, 20);
    }
}
