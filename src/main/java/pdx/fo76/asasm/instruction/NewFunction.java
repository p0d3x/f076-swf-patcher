package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class NewFunction extends Indented1ParamNode<StringLiteral> {

    public NewFunction(StringLiteral param) {
        super("newfunction", param, 20);
    }
}
