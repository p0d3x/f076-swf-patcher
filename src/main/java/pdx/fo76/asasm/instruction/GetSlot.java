package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class GetSlot extends Indented1ParamNode<Integer> {

    public GetSlot(Integer param) {
        super("getslot", param, 20);
    }
}
