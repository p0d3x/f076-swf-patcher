package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class NewObject extends Indented1ParamNode<Integer> {

    public NewObject(Integer param) {
        super(SyntaxConstants.NEW_OBJECT, param, 20);
    }
}
