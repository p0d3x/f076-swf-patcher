package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class SlotId extends SimpleNode {
    private final int index;
    public SlotId(int index) {
        super(SyntaxConstants.SLOT_ID);
        this.index = index;
    }

    @Override
    public String toString() {
        return getName() + " " + index;
    }
}
