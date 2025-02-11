package io.reactivex.parallel;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.LongConsumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.parallel.ParallelCollect;
import io.reactivex.internal.operators.parallel.ParallelConcatMap;
import io.reactivex.internal.operators.parallel.ParallelDoOnNextTry;
import io.reactivex.internal.operators.parallel.ParallelFilter;
import io.reactivex.internal.operators.parallel.ParallelFilterTry;
import io.reactivex.internal.operators.parallel.ParallelFlatMap;
import io.reactivex.internal.operators.parallel.ParallelFromArray;
import io.reactivex.internal.operators.parallel.ParallelFromPublisher;
import io.reactivex.internal.operators.parallel.ParallelJoin;
import io.reactivex.internal.operators.parallel.ParallelMap;
import io.reactivex.internal.operators.parallel.ParallelMapTry;
import io.reactivex.internal.operators.parallel.ParallelPeek;
import io.reactivex.internal.operators.parallel.ParallelReduce;
import io.reactivex.internal.operators.parallel.ParallelReduceFull;
import io.reactivex.internal.operators.parallel.ParallelRunOn;
import io.reactivex.internal.operators.parallel.ParallelSortedJoin;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.ListAddBiConsumer;
import io.reactivex.internal.util.MergerBiFunction;
import io.reactivex.internal.util.SorterFunction;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class ParallelFlowable<T> {
    public abstract int parallelism();

    public abstract void subscribe(Subscriber<? super T>[] subscriberArr);

    /* access modifiers changed from: protected */
    public final boolean validate(Subscriber<?>[] subscribers) {
        int p = parallelism();
        if (subscribers.length == p) {
            return true;
        }
        Throwable iae = new IllegalArgumentException("parallelism = " + p + ", subscribers = " + subscribers.length);
        for (Subscriber<?> s : subscribers) {
            EmptySubscription.error(iae, s);
        }
        return false;
    }

    @CheckReturnValue
    public static <T> ParallelFlowable<T> from(Publisher<? extends T> source) {
        return from(source, Runtime.getRuntime().availableProcessors(), Flowable.bufferSize());
    }

    @CheckReturnValue
    public static <T> ParallelFlowable<T> from(Publisher<? extends T> source, int parallelism) {
        return from(source, parallelism, Flowable.bufferSize());
    }

    @CheckReturnValue
    public static <T> ParallelFlowable<T> from(Publisher<? extends T> source, int parallelism, int prefetch) {
        ObjectHelper.requireNonNull(source, "source");
        ObjectHelper.verifyPositive(parallelism, "parallelism");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelFromPublisher(source, parallelism, prefetch));
    }

    @CheckReturnValue
    public final <R> R as(ParallelFlowableConverter<T, R> converter) {
        return ((ParallelFlowableConverter) ObjectHelper.requireNonNull(converter, "converter is null")).apply(this);
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> map(Function<? super T, ? extends R> mapper) {
        ObjectHelper.requireNonNull(mapper, "mapper");
        return RxJavaPlugins.onAssembly(new ParallelMap(this, mapper));
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> map(Function<? super T, ? extends R> mapper, ParallelFailureHandling errorHandler) {
        ObjectHelper.requireNonNull(mapper, "mapper");
        ObjectHelper.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelMapTry(this, mapper, errorHandler));
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> map(Function<? super T, ? extends R> mapper, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler) {
        ObjectHelper.requireNonNull(mapper, "mapper");
        ObjectHelper.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelMapTry(this, mapper, errorHandler));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> filter(Predicate<? super T> predicate) {
        ObjectHelper.requireNonNull(predicate, "predicate");
        return RxJavaPlugins.onAssembly(new ParallelFilter(this, predicate));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> filter(Predicate<? super T> predicate, ParallelFailureHandling errorHandler) {
        ObjectHelper.requireNonNull(predicate, "predicate");
        ObjectHelper.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelFilterTry(this, predicate, errorHandler));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> filter(Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler) {
        ObjectHelper.requireNonNull(predicate, "predicate");
        ObjectHelper.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelFilterTry(this, predicate, errorHandler));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> runOn(Scheduler scheduler) {
        return runOn(scheduler, Flowable.bufferSize());
    }

    @CheckReturnValue
    public final ParallelFlowable<T> runOn(Scheduler scheduler, int prefetch) {
        ObjectHelper.requireNonNull(scheduler, "scheduler");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelRunOn(this, scheduler, prefetch));
    }

    @CheckReturnValue
    public final Flowable<T> reduce(BiFunction<T, T, T> reducer) {
        ObjectHelper.requireNonNull(reducer, "reducer");
        return RxJavaPlugins.onAssembly(new ParallelReduceFull(this, reducer));
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> reduce(Callable<R> initialSupplier, BiFunction<R, ? super T, R> reducer) {
        ObjectHelper.requireNonNull(initialSupplier, "initialSupplier");
        ObjectHelper.requireNonNull(reducer, "reducer");
        return RxJavaPlugins.onAssembly(new ParallelReduce(this, initialSupplier, reducer));
    }

    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> sequential() {
        return sequential(Flowable.bufferSize());
    }

    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> sequential(int prefetch) {
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelJoin(this, prefetch, false));
    }

    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> sequentialDelayError() {
        return sequentialDelayError(Flowable.bufferSize());
    }

    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport("none")
    public final Flowable<T> sequentialDelayError(int prefetch) {
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelJoin(this, prefetch, true));
    }

    @CheckReturnValue
    public final Flowable<T> sorted(Comparator<? super T> comparator) {
        return sorted(comparator, 16);
    }

    @CheckReturnValue
    public final Flowable<T> sorted(Comparator<? super T> comparator, int capacityHint) {
        ObjectHelper.requireNonNull(comparator, "comparator is null");
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return RxJavaPlugins.onAssembly(new ParallelSortedJoin(reduce(Functions.createArrayList((capacityHint / parallelism()) + 1), ListAddBiConsumer.instance()).map(new SorterFunction(comparator)), comparator));
    }

    @CheckReturnValue
    public final Flowable<List<T>> toSortedList(Comparator<? super T> comparator) {
        return toSortedList(comparator, 16);
    }

    @CheckReturnValue
    public final Flowable<List<T>> toSortedList(Comparator<? super T> comparator, int capacityHint) {
        ObjectHelper.requireNonNull(comparator, "comparator is null");
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return RxJavaPlugins.onAssembly(reduce(Functions.createArrayList((capacityHint / parallelism()) + 1), ListAddBiConsumer.instance()).map(new SorterFunction(comparator)).reduce(new MergerBiFunction(comparator)));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> doOnNext(Consumer<? super T> onNext) {
        ObjectHelper.requireNonNull(onNext, "onNext is null");
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, onNext, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> doOnNext(Consumer<? super T> onNext, ParallelFailureHandling errorHandler) {
        ObjectHelper.requireNonNull(onNext, "onNext is null");
        ObjectHelper.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelDoOnNextTry(this, onNext, errorHandler));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> doOnNext(Consumer<? super T> onNext, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler) {
        ObjectHelper.requireNonNull(onNext, "onNext is null");
        ObjectHelper.requireNonNull(errorHandler, "errorHandler is null");
        return RxJavaPlugins.onAssembly(new ParallelDoOnNextTry(this, onNext, errorHandler));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> doAfterNext(Consumer<? super T> onAfterNext) {
        ObjectHelper.requireNonNull(onAfterNext, "onAfterNext is null");
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), onAfterNext, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> doOnError(Consumer<Throwable> onError) {
        ObjectHelper.requireNonNull(onError, "onError is null");
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), onError, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> doOnComplete(Action onComplete) {
        ObjectHelper.requireNonNull(onComplete, "onComplete is null");
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), onComplete, Functions.EMPTY_ACTION, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> doAfterTerminated(Action onAfterTerminate) {
        ObjectHelper.requireNonNull(onAfterTerminate, "onAfterTerminate is null");
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, onAfterTerminate, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> doOnSubscribe(Consumer<? super Subscription> onSubscribe) {
        ObjectHelper.requireNonNull(onSubscribe, "onSubscribe is null");
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, onSubscribe, Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> doOnRequest(LongConsumer onRequest) {
        ObjectHelper.requireNonNull(onRequest, "onRequest is null");
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.emptyConsumer(), onRequest, Functions.EMPTY_ACTION));
    }

    @CheckReturnValue
    public final ParallelFlowable<T> doOnCancel(Action onCancel) {
        ObjectHelper.requireNonNull(onCancel, "onCancel is null");
        return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, onCancel));
    }

    @CheckReturnValue
    public final <C> ParallelFlowable<C> collect(Callable<? extends C> collectionSupplier, BiConsumer<? super C, ? super T> collector) {
        ObjectHelper.requireNonNull(collectionSupplier, "collectionSupplier is null");
        ObjectHelper.requireNonNull(collector, "collector is null");
        return RxJavaPlugins.onAssembly(new ParallelCollect(this, collectionSupplier, collector));
    }

    @CheckReturnValue
    public static <T> ParallelFlowable<T> fromArray(Publisher<T>... publishers) {
        if (publishers.length != 0) {
            return RxJavaPlugins.onAssembly(new ParallelFromArray(publishers));
        }
        throw new IllegalArgumentException("Zero publishers not supported");
    }

    @CheckReturnValue
    public final <U> U to(Function<? super ParallelFlowable<T>, U> converter) {
        try {
            return ((Function) ObjectHelper.requireNonNull(converter, "converter is null")).apply(this);
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            throw ExceptionHelper.wrapOrThrow(ex);
        }
    }

    @CheckReturnValue
    public final <U> ParallelFlowable<U> compose(ParallelTransformer<T, U> composer) {
        return RxJavaPlugins.onAssembly(((ParallelTransformer) ObjectHelper.requireNonNull(composer, "composer is null")).apply(this));
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return flatMap(mapper, false, Integer.MAX_VALUE, Flowable.bufferSize());
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError) {
        return flatMap(mapper, delayError, Integer.MAX_VALUE, Flowable.bufferSize());
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError, int maxConcurrency) {
        return flatMap(mapper, delayError, maxConcurrency, Flowable.bufferSize());
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError, int maxConcurrency, int prefetch) {
        ObjectHelper.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelFlatMap(this, mapper, delayError, maxConcurrency, prefetch));
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return concatMap(mapper, 2);
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch) {
        ObjectHelper.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelConcatMap(this, mapper, prefetch, ErrorMode.IMMEDIATE));
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> mapper, boolean tillTheEnd) {
        return concatMapDelayError(mapper, 2, tillTheEnd);
    }

    @CheckReturnValue
    public final <R> ParallelFlowable<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch, boolean tillTheEnd) {
        ObjectHelper.requireNonNull(mapper, "mapper is null");
        ObjectHelper.verifyPositive(prefetch, "prefetch");
        return RxJavaPlugins.onAssembly(new ParallelConcatMap(this, mapper, prefetch, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY));
    }
}
