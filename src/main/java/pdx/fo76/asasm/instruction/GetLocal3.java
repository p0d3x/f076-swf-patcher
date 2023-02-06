package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class GetLocal3 extends IndentedSimpleNode {
    public GetLocal3() {
        super("getlocal3");
    }
}
