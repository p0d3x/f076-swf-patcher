package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.Identifier;
import pdx.fo76.asasm.QName;

@EqualsAndHashCode(callSuper = true)
public class Coerce extends Indented1ParamNode<Identifier> {
    public Coerce(Identifier param) {
        super("coerce", param, 20);
    }
}
