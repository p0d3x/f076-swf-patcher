package pdx.fo76.asasm.syntax;

public interface Transformer<T, V> {
    default V transform(T arg) {
        return transform(arg, 0);
    }
    V transform(T arg, int pad);
}
