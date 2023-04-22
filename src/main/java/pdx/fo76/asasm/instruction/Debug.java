package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import pdx.fo76.asasm.SyntaxConstants;

@EqualsAndHashCode(callSuper = true)
public class Debug extends IndentedSimpleNode {
    private final int paramIndent;
    private int param1;
    private StringLiteral param2;
    private int param3;
    private int param4;

    public Debug(int p1, StringLiteral p2, int p3, int p4) {
        super(SyntaxConstants.DEBUG);
        this.param1 = p1;
        this.param2 = p2;
        this.param3 = p3;
        this.param4 = p4;
        this.paramIndent = 20;
    }

    @Override
    public String toString() {
        return StringUtils.rightPad(super.toString(), paramIndent - 1)
                + " " + param1 + ", " + param2 + ", " + param3 + ", " + param4;
    }
}
