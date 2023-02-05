package pdx.fo76.asasm.instruction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import pdx.fo76.asasm.Identifier;
import pdx.fo76.asasm.QName;

import java.util.stream.Stream;

@EqualsAndHashCode
@AllArgsConstructor
public class TraitSlot extends Node {
    private Identifier slot;
    private final Integer slotId;
    private final Identifier type;
    private final String initValue;

    @Override
    public String toString() {
        return "trait"
                + " slot " + slot
                + (slotId != null ? " slotid " + slotId : "")
                + (type != null ? " type " + type : "")
                + (initValue != null ? " value " + initValue : "")
                + " end";
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(StringUtils.repeat(" ", indent) + this), super.stream(indent));
    }

    @Override
    public String getName() {
        return "trait";
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
