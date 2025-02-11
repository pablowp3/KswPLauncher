package com.bumptech.glide.provider;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pools;
import com.bumptech.glide.load.engine.DecodePath;
import com.bumptech.glide.load.engine.LoadPath;
import com.bumptech.glide.load.resource.transcode.UnitTranscoder;
import com.bumptech.glide.util.MultiClassKey;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LoadPathCache {
    private static final LoadPath<?, ?, ?> NO_PATHS_SIGNAL = new LoadPath(Object.class, Object.class, Object.class, Collections.singletonList(new DecodePath(Object.class, Object.class, Object.class, Collections.emptyList(), new UnitTranscoder(), (Pools.Pool<List<Throwable>>) null)), (Pools.Pool<List<Throwable>>) null);
    private final ArrayMap<MultiClassKey, LoadPath<?, ?, ?>> cache = new ArrayMap<>();
    private final AtomicReference<MultiClassKey> keyRef = new AtomicReference<>();

    public boolean isEmptyLoadPath(LoadPath<?, ?, ?> path) {
        return NO_PATHS_SIGNAL.equals(path);
    }

    public <Data, TResource, Transcode> LoadPath<Data, TResource, Transcode> get(Class<Data> dataClass, Class<TResource> resourceClass, Class<Transcode> transcodeClass) {
        LoadPath<?, ?, ?> result;
        MultiClassKey key = getKey(dataClass, resourceClass, transcodeClass);
        synchronized (this.cache) {
            result = this.cache.get(key);
        }
        this.keyRef.set(key);
        return result;
    }

    public void put(Class<?> dataClass, Class<?> resourceClass, Class<?> transcodeClass, LoadPath<?, ?, ?> loadPath) {
        synchronized (this.cache) {
            this.cache.put(new MultiClassKey(dataClass, resourceClass, transcodeClass), loadPath != null ? loadPath : NO_PATHS_SIGNAL);
        }
    }

    private MultiClassKey getKey(Class<?> dataClass, Class<?> resourceClass, Class<?> transcodeClass) {
        MultiClassKey key = this.keyRef.getAndSet((Object) null);
        if (key == null) {
            key = new MultiClassKey();
        }
        key.set(dataClass, resourceClass, transcodeClass);
        return key;
    }
}
