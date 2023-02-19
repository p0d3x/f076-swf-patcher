package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Returns extends Indented1ParamNode<Identifier> {

    public Returns(Identifier param) {
        super("returns", param);
    }
}
