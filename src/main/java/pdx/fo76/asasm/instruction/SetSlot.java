package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class SetSlot extends Indented1ParamNode<Integer> {

    public SetSlot(Integer param) {
        super("setslot", param, 20);
    }
}
