package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class RefId extends Indented1ParamNode<StringLiteral> {
    public RefId(String param) {
        super("refid", new StringLiteral(param));
    }
}
