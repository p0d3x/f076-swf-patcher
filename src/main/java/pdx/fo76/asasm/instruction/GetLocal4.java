package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class GetLocal4 extends IndentedSimpleNode {
    public GetLocal4() {
        super("getlocal4");
    }
}
