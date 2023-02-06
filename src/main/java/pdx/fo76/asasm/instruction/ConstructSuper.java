package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ConstructSuper extends Indented1ParamNode<Integer> {

    public ConstructSuper(Integer param) {
        super("constructsuper", param, 20);
    }
}
