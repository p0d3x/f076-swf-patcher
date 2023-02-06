package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class GetLocal extends Indented1ParamNode<Integer> {
    public GetLocal(Integer param) {
        super("getlocal", param, 20);
    }
}
