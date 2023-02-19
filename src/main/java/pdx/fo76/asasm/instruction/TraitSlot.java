package pdx.fo76.asasm.instruction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TraitSlot extends Trait {
    private Slot slot;
    private SlotId slotId;
    private Type type;
    private Value value;

    @Override
    public String toString() {
        return getName()
                + (slot != null ? " " + slot : "")
                + (slotId != null ? " " + slotId : "")
                + (type != null ? " " + type : "")
                + (value != null ? " " + value : "")
                + " end";
    }

    @Override
    public void replaceScopes(QName[] qNamesToReplace, QName qNameReplacement) {
        for (QName qName : qNamesToReplace) {
            if (qName.equals(slot.getIdentifier())) {
                slot.setIdentifier(qName);
                return;
            }
        }
    }
}
