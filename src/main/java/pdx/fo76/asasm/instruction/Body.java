package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class Body extends IndentedSimpleNode {
    public Body() {
        super(SyntaxConstants.BODY);
    }
}
