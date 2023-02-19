package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
public class Slot extends SimpleNode {
    @Getter
    @Setter
    private Identifier identifier;

    public Slot(Identifier identifier) {
        super("slot");
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return getName() + " " + identifier;
    }
}
