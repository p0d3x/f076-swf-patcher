package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Value extends SimpleNode {

    private final String initValue;


    public Value(String initValue) {
        super("value");
        this.initValue = initValue;
    }

    @Override
    public String toString() {
        return getName() + " " + initValue;
    }
}
