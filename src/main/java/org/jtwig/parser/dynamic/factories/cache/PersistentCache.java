package org.jtwig.parser.dynamic.factories.cache;

import com.google.common.base.Supplier;

import java.util.HashMap;
import java.util.Map;

public class PersistentCache {
    private Map<Object, Object> cache = new HashMap<>();

    public <T> T get (Object key, Supplier<T> value) {
        if (!cache.containsKey(key)) {
            cache.put(key, value.get());
        }

        return (T) cache.get(key);
    }
}
