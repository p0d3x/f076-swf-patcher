package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Extends extends Indented1ParamNode<QName> {
    public Extends(QName param) {
        super("extends", param);
    }
}
