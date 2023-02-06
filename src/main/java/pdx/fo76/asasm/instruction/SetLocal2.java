package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class SetLocal2 extends IndentedSimpleNode {
    public SetLocal2() {
        super("setlocal2");
    }
}
