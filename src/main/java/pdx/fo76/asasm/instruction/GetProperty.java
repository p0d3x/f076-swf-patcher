package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.Identifier;
import pdx.fo76.asasm.QName;

@EqualsAndHashCode(callSuper = true)
public class GetProperty extends Indented1ParamNode<Identifier> {

    public GetProperty(Identifier param) {
        super("getproperty", param, 20);
    }

    @Override
    public void replaceScopes(QName[] qNamesToReplace, QName qNameReplacement) {
        if (getParam() != null) {
            for (QName toReplace : qNamesToReplace) {
                if (getParam().equals(toReplace)) {
                    setParam(qNameReplacement);
                }
            }
        }
        super.replaceScopes(qNamesToReplace, qNameReplacement);
    }
}
