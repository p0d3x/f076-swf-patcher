package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ConvertD extends IndentedSimpleNode {
    public ConvertD() {
        super("convert_d");
    }
}
