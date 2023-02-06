package pdx.fo76.asasm.instruction;

import pdx.fo76.asasm.QName;

import java.util.List;

public class TraitGetter extends TraitMethod {
    public TraitGetter(QName qName, List<Flag> flags) {
        super(qName, flags);
    }

    @Override
    public String getType() {
        return "getter";
    }
}
