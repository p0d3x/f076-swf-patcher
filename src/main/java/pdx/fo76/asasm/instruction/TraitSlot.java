package pdx.fo76.asasm.instruction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.Identifier;
import pdx.fo76.asasm.QName;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TraitSlot extends Trait {
    private Identifier slot;
    private final Integer slotId;
    private final Identifier type;
    private final String initValue;

    @Override
    public String toString() {
        return getName()
                + " slot " + slot
                + (slotId != null ? " slotid " + slotId : "")
                + (type != null ? " type " + type : "")
                + (initValue != null ? " value " + initValue : "")
                + " end";
    }

    @Override
    public void replaceScopes(QName[] qNamesToReplace, QName qNameReplacement) {
        for (QName qName : qNamesToReplace) {
            if (qName.equals(slot)) {
                slot = qNameReplacement;
                return;
            }
        }
    }
}
