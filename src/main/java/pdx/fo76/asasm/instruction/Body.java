package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Body extends IndentedSimpleNode {
    public Body() {
        super("body");
    }
}
