package pdx.fo76.asasm.instruction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public abstract class Indented1ParamNode<T> extends IndentedSimpleNode {
    private final int paramIndent;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private T param;

    protected Indented1ParamNode(String name, T param) {
        this(name, param, 0);
    }

    protected Indented1ParamNode(String name, T param, int paramIndent) {
        super(name);
        this.param = param;
        this.paramIndent = paramIndent;
    }

    @Override
    public String toString() {
        return StringUtils.rightPad(super.toString(), paramIndent - 1) + " " + param;
    }
}
