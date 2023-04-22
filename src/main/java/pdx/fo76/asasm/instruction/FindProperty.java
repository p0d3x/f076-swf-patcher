package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class FindProperty extends Indented1ParamNode<String> {

    public FindProperty(String param) {
        super(SyntaxConstants.FIND_PROPERTY, param, 20);
    }
}
