package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class GetLocal0 extends IndentedSimpleNode {
    public GetLocal0() {
        super("getlocal0");
    }
}
