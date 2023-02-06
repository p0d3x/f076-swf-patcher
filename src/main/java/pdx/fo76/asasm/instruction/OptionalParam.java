package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class OptionalParam extends Indented1ParamNode<String> {
    public OptionalParam(String param) {
        super("optional", param);
    }
}
