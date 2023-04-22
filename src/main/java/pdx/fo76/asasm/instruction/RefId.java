package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class RefId extends Indented1ParamNode<StringLiteral> {
    public RefId(StringLiteral param) {
        super(SyntaxConstants.REF_ID, param);
    }
}
