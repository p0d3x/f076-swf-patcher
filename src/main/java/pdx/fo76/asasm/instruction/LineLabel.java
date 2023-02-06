package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class LineLabel extends SimpleNode {

    public LineLabel(String name) {
        super(name);
    }
}
