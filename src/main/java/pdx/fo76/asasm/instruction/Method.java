package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Method extends IndentedSimpleNode {
    public Method() {
        super("method");
    }
}
