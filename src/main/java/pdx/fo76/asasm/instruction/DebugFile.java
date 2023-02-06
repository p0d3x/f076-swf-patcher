package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class DebugFile extends Indented1ParamNode<StringLiteral> {

    public DebugFile(String param) {
        super("debugfile", new StringLiteral(param), 20);
    }
}
