package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ParamName extends Indented1ParamNode<StringLiteral> {

    public ParamName(String param) {
        super("paramname", new StringLiteral(param));
    }
}
