package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class Name extends Indented1ParamNode<StringLiteral> {
    public Name(StringLiteral param) {
        super(SyntaxConstants.NAME, param);
    }
}
