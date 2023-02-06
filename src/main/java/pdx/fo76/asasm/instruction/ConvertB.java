package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ConvertB extends IndentedSimpleNode {
    public ConvertB() {
        super("convert_b");
    }
}
