package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Code extends IndentedSimpleNode {
    public Code() {
        super("code");
    }
}
