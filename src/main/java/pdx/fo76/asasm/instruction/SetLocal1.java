package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class SetLocal1 extends IndentedSimpleNode {
    public SetLocal1() {
        super("setlocal1");
    }
}
