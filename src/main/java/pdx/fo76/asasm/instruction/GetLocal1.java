package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class GetLocal1 extends IndentedSimpleNode {
    public GetLocal1() {
        super("getlocal1");
    }
}
