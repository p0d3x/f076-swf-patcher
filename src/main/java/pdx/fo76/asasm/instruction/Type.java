package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Type extends SimpleNode {

    private final Identifier type;

    public Type(Identifier type) {
        super("type");
        this.type = type;
    }

    @Override
    public String toString() {
        return getName() + " " + type;
    }
}
