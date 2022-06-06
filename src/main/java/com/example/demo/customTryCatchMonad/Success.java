package com.example.demo.customTryCatchMonad;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class Success<T> extends Try<T> {

    private final T value;

    Success(T value) {
        this.value = value;
    }

    @Override
    public <U> Try<U> map(Function<? super T, ? extends U> f) {
        Objects.requireNonNull(f);

        try {
            return new Success<>(f.apply(value));
        } catch (Throwable e) {
            return Try.failure(e);
        }
    }

    @Override
    public <U> Try<U> flatMap(Function<? super T, Try<U>> f) {
        return f.apply(value);
    }

    @Override
    public Try<T> filter(Predicate<T> pred) {
        if(pred.test(value))
            return this;
        else {
            return Try.failure(new NoSuchElementException("Predicate doesn't match for " + value));
        }
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T orElse(T value) {
        return this.value;
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.ofNullable(value);
    }

    @Override
    public boolean isSuccessful() {
        return true;
    }

    @Override
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        return value;
    }

    @Override
    protected <E extends Throwable> Try<T> onSuccess(ThrowableConsumer<T, E> action) throws E {
        action.accept(value);
        return this;
    }

    @Override
    protected <E extends Throwable> Try<T> onFailure(Function<Throwable, E> action) throws E {
        return this;
    }

    @Override
    public T getUnchecked() {
        return value;
    }
}