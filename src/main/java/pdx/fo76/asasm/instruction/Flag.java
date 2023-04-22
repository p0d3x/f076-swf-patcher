package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class Flag extends Indented1ParamNode<String> {
    public Flag(String param) {
        super(SyntaxConstants.FLAG, param);
    }
}
