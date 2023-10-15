package pdx.fo76.asasm.syntax;

import java.util.NoSuchElementException;

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

    record Left<S, T>(S value) implements Either<S, T> {

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
            throw new NoSuchElementException();
        }
    }

    record Right<S, T>(T value) implements Either<S, T> {

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
            throw new NoSuchElementException();
        }

        @Override
        public T right() {
            return value;
        }
    }
}
