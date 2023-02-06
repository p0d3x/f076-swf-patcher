package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Comment extends Indented1ParamNode<String> {

    public Comment(String param) {
        super(";", param);
    }
}
