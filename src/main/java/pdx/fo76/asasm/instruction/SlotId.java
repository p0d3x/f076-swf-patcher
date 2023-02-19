package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class SlotId extends SimpleNode {
    private final int slotId;
    public SlotId(int slotId) {
        super("slotid");
        this.slotId = slotId;
    }

    @Override
    public String toString() {
        return getName() + " " + slotId;
    }
}
