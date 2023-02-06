package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Flag extends Indented1ParamNode<String> {
    public Flag(String param) {
        super("flag", param);
    }
}
