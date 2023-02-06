package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ReturnValue extends IndentedSimpleNode {
    public ReturnValue() {
        super("returnvalue");
    }
}
