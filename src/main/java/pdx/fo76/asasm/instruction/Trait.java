package pdx.fo76.asasm.instruction;

import pdx.fo76.asasm.SyntaxConstants;

public abstract class Trait extends IndentedSimpleNode {

    protected Trait() {
        super(SyntaxConstants.TRAIT);
    }
}
