package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Iinit extends IndentedSimpleNode {
    public Iinit() {
        super("iinit");
    }
}
