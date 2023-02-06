package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class GetLocal2 extends IndentedSimpleNode {
    public GetLocal2() {
        super("getlocal2");
    }
}
