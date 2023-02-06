package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.Identifier;
import pdx.fo76.asasm.QName;

@EqualsAndHashCode(callSuper = true)
public class Returns extends Indented1ParamNode<Identifier> {

    public Returns(Identifier param) {
        super("returns", param);
    }
}
