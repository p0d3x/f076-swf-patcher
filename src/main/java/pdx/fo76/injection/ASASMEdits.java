package pdx.fo76.injection;

import lombok.extern.slf4j.Slf4j;
import pdx.fo76.asasm.instruction.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static pdx.fo76.asasm.SyntaxConstants.*;


@Slf4j
public class ASASMEdits {

    private ASASMEdits() {}

    public static ASASMEdit injectSFE() {
        return new ASASMEdit() {
            @Override
            public void execute(PatcherStage<?> context, String namespace, String className, Node classNode) throws EditException {
                var instance = classNode.m(INSTANCE);
                var slotQName = QName.ofPackage("__SFCodeObj");
                var sfCodeObj = new Slot(slotQName);
                var typeQName = QName.ofPackage("Object");
                var object = new Type(typeQName);
                // declare and initialize __SFCodeObj
                // after iinit
                instance.insertAfter(IINIT, List.of(
                        new TraitSlot(sfCodeObj, null, object, null)
                ));
                // after first: pushscope
                instance.m(IINIT, BODY, CODE)
                        .insertAfter(PUSH_SCOPE, List.of(
                                new BlankLine(),
                                new GetLocal0(),
                                new FindPropStrict(typeQName),
                                new ConstructProp(typeQName, 0),
                                new InitProperty(slotQName)
                        ), 2);
                log.info("injected __SFCodeObj into {}", className);
            }
        };
    }

    public static ASASMEdit injectLoader(String modFile, String asmFile) {
        Objects.requireNonNull(modFile);
        Objects.requireNonNull(asmFile);
        return new ASASMEdit() {
            @Override
            public void execute(PatcherStage<?> context, String namespace, String className, Node classNode) throws EditException {
                try {
                    var instance = classNode.m(INSTANCE);
                    // declare and call loadMod
                    // before end
                    var loaderNode = context.readNodeFromTemplate(asmFile, className);
                    instance.insertBeforeLast(END, loaderNode.getInstructions());
                    // before last: returnvoid
                    var loadMod = QName.of(Namespace.ofPrivate(className), "loadMod");
                    instance.m(IINIT, BODY, CODE)
                            .insertBeforeLast(RETURN_VOID, List.of(
                                    new GetLocal0(),
                                    new PushString(new StringLiteral(modFile)),
                                    new CallPropVoid(loadMod, 1),
                                    new BlankLine()
                            ));
                    log.info("injected loadMod('{}') into {}", modFile, className);
                } catch (IOException e) {
                    throw new EditException("IOException during edit: " + e.getMessage(), e);
                }
            }
        };
    }

    public static ASASMEdit changePrivateScopeToPackage(String fieldName) {
        Objects.requireNonNull(fieldName);
        return new ASASMEdit() {
            @Override
            public void execute(PatcherStage<?> context, String namespace, String className, Node codeNode) throws EditException {
                // Replace the text in each line
                QName[] scopesToReplace = {
                        QName.of(Namespace.ofPrivate(className), fieldName),
                        QName.of(Namespace.ofPrivate(null, className), fieldName),
                        QName.of(Namespace.ofPrivate(null, className + "/instance#0"), fieldName),
                };
                var replacementScope = QName.ofPackage(fieldName);
                codeNode.replaceScopes(scopesToReplace, replacementScope);
                log.info("made {} readable in {}", fieldName, className);
            }
        };
    }

    public static ASASMEdit patchQuantityMenu() {
        return new ASASMEdit() {
            @Override
            public void execute(PatcherStage<?> context, String namespace, String className, Node classNode) throws EditException {

                try {
                    var fqName = (namespace != null ? namespace + ":" : "") + className;
                    var instance = classNode.m(INSTANCE);

                    // before first trait slot
                    var slot = new Slot(QName.of(Namespace.ofPrivate(null, fqName), "isEditingValue"));
                    var type = new Type(QName.ofPackage("Boolean"));
                    instance.insertAfter(IINIT, List.of(
                            new TraitSlot(slot, null, type, new Value("False()"))
                    ));

                    var loaderNode = context.readNodeFromTemplate("quantitymenu_ctor.asasm", fqName);
                    instance.m(IINIT, BODY, CODE)
                            .insertBeforeLast(RETURN_VOID, loaderNode.getInstructions());

                    var openMenuNode = context.readNodeFromTemplate("quantitymenu_openmenu.asasm", fqName);
                    instance.methodCode("OpenMenu")
                            .insertBeforeLast(RETURN_VOID, openMenuNode.getInstructions());

                    var closeMenuNode = context.readNodeFromTemplate("quantitymenu_closemenu.asasm", fqName);
                    instance.methodCode("CloseMenu")
                            .insertBeforeLast(RETURN_VOID, closeMenuNode.getInstructions());

                    // replace class/instance/onKeyDowwn
                    var onKeyDownNode = context.readNodeFromTemplate("quantitymenu_onKeyDown.asasm", fqName);
                    instance.replaceMethod("onKeyDown", onKeyDownNode.getInstructions().get(0));

                    // insert class/instance/updateValueInput
                    var updateValueInputNode = context.readNodeFromTemplate("quantitymenu_updateValueInput.asasm", fqName);
                    instance.insertAfterMethod("onKeyDown", updateValueInputNode.getInstructions().get(0));

                    // insert class/instance/onValueClicked
                    var onValueClickedNode = context.readNodeFromTemplate("quantitymenu_onValueClicked.asasm", fqName);
                    instance.insertAfterMethod("updateValueInput", onValueClickedNode.getInstructions().get(0));

                    log.info("added TextField input to QuantityMenu");
                } catch (IOException e){
                    throw new EditException("IOException during edit: " + e.getMessage(), e);
                }
            }
        };
    }
}
