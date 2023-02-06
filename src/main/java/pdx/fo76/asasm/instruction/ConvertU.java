package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ConvertU extends IndentedSimpleNode {
    public ConvertU() {
        super("convert_u");
    }
}
