package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.Identifier;
import pdx.fo76.asasm.QName;

@EqualsAndHashCode(callSuper = true)
public class Param extends Indented1ParamNode<Identifier> {
    public Param(Identifier param) {
        super("param", param);
    }
}
