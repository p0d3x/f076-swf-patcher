package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class DebugFile extends Indented1ParamNode<StringLiteral> {

    public DebugFile(StringLiteral param) {
        super(SyntaxConstants.DEBUG_FILE, param, 20);
    }
}
