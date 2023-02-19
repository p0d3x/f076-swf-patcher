package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ProtectedNS extends Indented1ParamNode<Namespace> {
    public ProtectedNS(Namespace param) {
        super("protectedns", param);
    }
}
