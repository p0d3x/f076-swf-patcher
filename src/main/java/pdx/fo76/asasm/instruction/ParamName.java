package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ParamName extends Indented1ParamNode<StringLiteral> {

    public ParamName(StringLiteral param) {
        super("paramname", param);
    }
}
