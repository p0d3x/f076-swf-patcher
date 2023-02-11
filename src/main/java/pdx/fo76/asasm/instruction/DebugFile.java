package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class DebugFile extends Indented1ParamNode<StringLiteral> {

    public DebugFile(StringLiteral param) {
        super("debugfile", param, 20);
    }
}
