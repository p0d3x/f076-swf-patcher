package pdx.fo76.asasm.syntax;

public interface Transformer<V> {
    default V transform(Syntax arg) {
        return transform(arg, 0);
    }
    V transform(Syntax arg, int pad);
}
