package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Instance extends Indented1ParamNode<QName> {

    public Instance(QName qName) {
        super("instance", qName);
    }
}
