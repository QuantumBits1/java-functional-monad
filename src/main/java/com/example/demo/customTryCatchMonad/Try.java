package com.example.demo.customTryCatchMonad;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Try<T> {

    public static <U> Try<U> ofThrowable(Supplier<U> f) {
        Objects.requireNonNull(f);
        try {
            return Try.successful(f.get());
        } catch (Throwable e) {
            return Try.failure(e);
        }
    }

    /**
     * Transform the successful value or pass through the throwable.
     * Optional if wanting to change type <U> from original, must
     * be set if wanting to change.
     *
     * @param f the function to be applied
     * @param <U> new type if changing <Optional>
     * @return the Success or Failure value
     */
    protected abstract <U> Try<U> map(Function<? super T, ? extends U> f);

    /**
     * Transform the successful value or pass through the throwable.
     * Taking a Try<U> as the result
     * Optional if wanting to change type <U> from original, must
     * be set if wanting to change.
     *
     * @param f the function to be applied
     * @param <U> new type if changing <Optional>
     * @return a new Try (Try.class)
     */
    protected abstract <U> Try<U> flatMap(Function<? super T, Try<U>> f);

    /**
     * If a Try is a Success and the predicate holds true, the Success is passed further.
     * Otherwise (Failure or predicate doesn't hold), pass Failure.
     *
     * @param pred predicate applied to the value held by Try
     * @return For Success, the same success if predicate holds true, otherwise Failure
     */
    protected abstract Try<T> filter (Predicate<T> pred);

    /**
     * Gets the value T or throws an exception
     *
     * @return T
     * @throws Throwable
     */
    protected abstract T get() throws Throwable;

    /**
     * Return a value in the case of a failure.
     *
     * @param value return the try's value or else the value specified.
     * @return new Try
     */
    protected abstract T orElse(T value);

    /**
     * Try contents wrapped in Optional of nullable.
     *
     * @return Optional of T, if original action Succeeded, Empty or Null if original action failed
     */
    protected abstract Optional<T> toOptional();

    /**
     * Returns if original action failed or succeeded.
     *
     * @return boolean
     */
    protected abstract boolean isSuccessful();

    /**
     * Gets the value T on Success or throws the cause of the failure.
     *
     * @return T
     * @throws Throwable produced by the supplier function argument
     */
    protected abstract <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

    /**
     * Applies the provided action if original action was successful
     *
     * @param action action to run
     * @return new Try
     * @throws E if the action threw an exception
     */
    protected abstract <E extends Throwable> Try<T> onSuccess(ThrowableConsumer<T, E> action) throws E;

    /**
     * Applies the provided action, if original action failed
     *
     * @param action action to run
     * @return new Try
     * @throws E if the action threw an exception
     */
    protected abstract <E extends Throwable> Try<T> onFailure(Function<Throwable, E> action) throws E;

    /**
     * Gets the value T on Success or throws the original exception wrapped in a RuntimeException
     * @return T
     * @throws RuntimeException
     */
    protected abstract T getUnchecked();

    /**
     * Factory method for failure object.
     *
     * @param e throwable to create the failed Try with
     * @param <U> Type
     * @return a new Failure
     */
    static <U> Failure<U> failure(Throwable e) {
        return new Failure<>(e);
    }

    /**
     * Factory method for success object.
     *
     * @param x value to create the successful Try with
     * @param <U> Type
     * @return a new Success
     */
    static <U> Success<U> successful(U x) {
        return new Success<>(x);
    }
}
