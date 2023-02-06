package pdx.fo76.asasm.instruction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.Identifier;
import pdx.fo76.asasm.QName;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TraitConst extends Trait {
    private Identifier qName;
    private final Integer slotId;
    private final Identifier type;
    private final String initValue;

    @Override
    public String toString() {
        return getName()
                + " const " + qName
                + (slotId != null ? " slotid " + slotId : "")
                + (type != null ? " type " + type : "")
                + (initValue != null ? " value " + initValue : "")
                + " end";
    }

    @Override
    public void replaceScopes(QName[] qNamesToReplace, QName qNameReplacement) {
        for (QName toReplace : qNamesToReplace) {
            if (toReplace.equals(qName)) {
                qName = qNameReplacement;
                return;
            }
        }
    }
}
