package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class NewFunction extends Indented1ParamNode<StringLiteral> {

    public NewFunction(StringLiteral param) {
        super(SyntaxConstants.NEW_FUNCTION, param, 20);
    }
}
