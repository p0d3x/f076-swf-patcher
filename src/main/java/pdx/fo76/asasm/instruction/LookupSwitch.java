package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class LookupSwitch extends Indented1ParamNode<String> {

    public LookupSwitch(String param) {
        super(SyntaxConstants.LOOKUP_SWITCH, param, 20);
    }
}
