package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class RefId extends Indented1ParamNode<StringLiteral> {
    public RefId(StringLiteral param) {
        super("refid", param);
    }
}
