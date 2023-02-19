package pdx.fo76.asasm.syntax;

import lombok.Value;

public interface Either<S, T> {

    boolean isLeft();
    boolean isRight();
    S left();
    T right();

    static <S, T> Either<S, T> left(S val) {
        return new Left<>(val);
    }

    static <S, T> Either<S, T> right(T val) {
        return new Right<>(val);
    }

    @Value
    class Left<S, T> implements Either<S, T> {

        S value;

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public S left() {
            return value;
        }

        @Override
        public T right() {
            throw new RuntimeException();
        }
    }

    @Value
    class Right<S, T> implements Either<S, T> {

        T value;

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public S left() {
            throw new RuntimeException();
        }

        @Override
        public T right() {
            return value;
        }
    }
}
