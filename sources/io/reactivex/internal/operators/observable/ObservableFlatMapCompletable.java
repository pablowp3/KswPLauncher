package io.reactivex.internal.operators.observable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMapCompletable<T> extends AbstractObservableWithUpstream<T, T> {
    final boolean delayErrors;
    final Function<? super T, ? extends CompletableSource> mapper;

    public ObservableFlatMapCompletable(ObservableSource<T> source, Function<? super T, ? extends CompletableSource> mapper2, boolean delayErrors2) {
        super(source);
        this.mapper = mapper2;
        this.delayErrors = delayErrors2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new FlatMapCompletableMainObserver(observer, this.mapper, this.delayErrors));
    }

    static final class FlatMapCompletableMainObserver<T> extends BasicIntQueueDisposable<T> implements Observer<T> {
        private static final long serialVersionUID = 8443155186132538303L;
        final boolean delayErrors;
        volatile boolean disposed;
        final Observer<? super T> downstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final Function<? super T, ? extends CompletableSource> mapper;
        final CompositeDisposable set = new CompositeDisposable();
        Disposable upstream;

        FlatMapCompletableMainObserver(Observer<? super T> observer, Function<? super T, ? extends CompletableSource> mapper2, boolean delayErrors2) {
            this.downstream = observer;
            this.mapper = mapper2;
            this.delayErrors = delayErrors2;
            lazySet(1);
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.upstream, d)) {
                this.upstream = d;
                this.downstream.onSubscribe(this);
            }
        }

        public void onNext(T value) {
            try {
                CompletableSource cs = (CompletableSource) ObjectHelper.requireNonNull(this.mapper.apply(value), "The mapper returned a null CompletableSource");
                getAndIncrement();
                FlatMapCompletableMainObserver<T>.InnerObserver inner = new InnerObserver();
                if (!this.disposed && this.set.add(inner)) {
                    cs.subscribe(inner);
                }
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                this.upstream.dispose();
                onError(ex);
            }
        }

        public void onError(Throwable e) {
            if (!this.errors.addThrowable(e)) {
                RxJavaPlugins.onError(e);
            } else if (!this.delayErrors) {
                dispose();
                if (getAndSet(0) > 0) {
                    this.downstream.onError(this.errors.terminate());
                }
            } else if (decrementAndGet() == 0) {
                this.downstream.onError(this.errors.terminate());
            }
        }

        public void onComplete() {
            if (decrementAndGet() == 0) {
                Throwable ex = this.errors.terminate();
                if (ex != null) {
                    this.downstream.onError(ex);
                } else {
                    this.downstream.onComplete();
                }
            }
        }

        public void dispose() {
            this.disposed = true;
            this.upstream.dispose();
            this.set.dispose();
        }

        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        public T poll() throws Exception {
            return null;
        }

        public boolean isEmpty() {
            return true;
        }

        public void clear() {
        }

        public int requestFusion(int mode) {
            return mode & 2;
        }

        /* access modifiers changed from: package-private */
        public void innerComplete(FlatMapCompletableMainObserver<T>.InnerObserver inner) {
            this.set.delete(inner);
            onComplete();
        }

        /* access modifiers changed from: package-private */
        public void innerError(FlatMapCompletableMainObserver<T>.InnerObserver inner, Throwable e) {
            this.set.delete(inner);
            onError(e);
        }

        final class InnerObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
            private static final long serialVersionUID = 8606673141535671828L;

            InnerObserver() {
            }

            public void onSubscribe(Disposable d) {
                DisposableHelper.setOnce(this, d);
            }

            public void onComplete() {
                FlatMapCompletableMainObserver.this.innerComplete(this);
            }

            public void onError(Throwable e) {
                FlatMapCompletableMainObserver.this.innerError(this, e);
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            public boolean isDisposed() {
                return DisposableHelper.isDisposed((Disposable) get());
            }
        }
    }
}
