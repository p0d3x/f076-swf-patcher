package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Type extends SimpleNode {

    private final Identifier identifier;

    public Type(Identifier identifier) {
        super("type");
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return getName() + " " + identifier;
    }
}
