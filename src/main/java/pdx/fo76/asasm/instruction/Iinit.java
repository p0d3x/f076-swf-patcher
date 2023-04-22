package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class Iinit extends IndentedSimpleNode {
    public Iinit() {
        super(SyntaxConstants.IINIT);
    }
}
