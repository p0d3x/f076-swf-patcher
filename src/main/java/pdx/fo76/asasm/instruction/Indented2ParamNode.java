package pdx.fo76.asasm.instruction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public abstract class Indented2ParamNode<T1, T2> extends IndentedSimpleNode {
    private final int paramIndent;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private T1 param1;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private T2 param2;

    protected Indented2ParamNode(String name, T1 param1, T2 param2) {
        this(name, param1, param2, 0);
    }

    protected Indented2ParamNode(String name, T1 param1, T2 param2, int paramIndent) {
        super(name);
        this.param1 = param1;
        this.param2 = param2;
        this.paramIndent = paramIndent;
    }

    @Override
    public String toString() {
        return StringUtils.rightPad(super.toString(), paramIndent - 1) + " " + param1 + ", " + param2;
    }
}
