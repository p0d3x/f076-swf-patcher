package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class SetLocal3 extends IndentedSimpleNode {
    public SetLocal3() {
        super("setlocal3");
    }
}
