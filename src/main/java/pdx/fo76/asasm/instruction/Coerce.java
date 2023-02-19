package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Coerce extends Indented1ParamNode<Identifier> {
    public Coerce(Identifier param) {
        super("coerce", param, 20);
    }
}
