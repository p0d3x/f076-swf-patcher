package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.QName;

@EqualsAndHashCode(callSuper = true)
public class Instance extends Indented1ParamNode<QName> {

    public Instance(QName qName) {
        super("instance", qName);
    }
}
