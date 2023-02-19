package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Param extends Indented1ParamNode<Identifier> {
    public Param(Identifier param) {
        super("param", param);
    }
}
