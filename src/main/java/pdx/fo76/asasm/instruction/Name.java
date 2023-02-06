package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Name extends Indented1ParamNode<StringLiteral> {
    public Name(String param) {
        super("name", new StringLiteral(param));
    }
}
