package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class FindPropStrict extends Indented1ParamNode<Identifier> {

    public FindPropStrict(Identifier param) {
        super("findpropstrict", param, 20);
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
