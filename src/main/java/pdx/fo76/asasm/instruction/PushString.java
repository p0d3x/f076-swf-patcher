package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PushString extends Indented1ParamNode<StringLiteral> {

    public PushString(StringLiteral param) {
        super("pushstring", param, 20);
    }
}
