package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableTakeUntilPredicate<T> extends AbstractObservableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    public ObservableTakeUntilPredicate(ObservableSource<T> source, Predicate<? super T> predicate2) {
        super(source);
        this.predicate = predicate2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new TakeUntilPredicateObserver(observer, this.predicate));
    }

    static final class TakeUntilPredicateObserver<T> implements Observer<T>, Disposable {
        boolean done;
        final Observer<? super T> downstream;
        final Predicate<? super T> predicate;
        Disposable upstream;

        TakeUntilPredicateObserver(Observer<? super T> downstream2, Predicate<? super T> predicate2) {
            this.downstream = downstream2;
            this.predicate = predicate2;
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.upstream, d)) {
                this.upstream = d;
                this.downstream.onSubscribe(this);
            }
        }

        public void dispose() {
            this.upstream.dispose();
        }

        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                this.downstream.onNext(t);
                try {
                    if (this.predicate.test(t)) {
                        this.done = true;
                        this.upstream.dispose();
                        this.downstream.onComplete();
                    }
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    this.upstream.dispose();
                    onError(e);
                }
            }
        }

        public void onError(Throwable t) {
            if (!this.done) {
                this.done = true;
                this.downstream.onError(t);
                return;
            }
            RxJavaPlugins.onError(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.downstream.onComplete();
            }
        }
    }
}
