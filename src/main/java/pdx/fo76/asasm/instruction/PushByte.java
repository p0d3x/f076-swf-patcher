package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PushByte extends Indented1ParamNode<Integer> {

    public PushByte(Integer param) {
        super("pushbyte", param, 20);
    }
}
