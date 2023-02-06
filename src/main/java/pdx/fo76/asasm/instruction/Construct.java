package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Construct extends Indented1ParamNode<Integer> {

    public Construct(Integer param) {
        super("construct", param, 20);
    }
}
