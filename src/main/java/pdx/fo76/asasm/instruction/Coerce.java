package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class Coerce extends Indented1ParamNode<Identifier> {
    public Coerce(Identifier param) {
        super(SyntaxConstants.COERCE, param, 20);
    }
}
