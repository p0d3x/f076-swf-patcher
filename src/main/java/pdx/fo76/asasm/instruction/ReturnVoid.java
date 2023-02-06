package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ReturnVoid extends IndentedSimpleNode {
    public ReturnVoid() {
        super("returnvoid");
    }
}
