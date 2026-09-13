package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class IdGenerator {
    private static final IdGenerator EAGER_INSTANCE = new IdGenerator();
    private static IdGenerator lazyInstance;
    private static final AtomicInteger COUNTER;

    static {
        COUNTER = new AtomicInteger(1000);
    }

    private IdGenerator() {
    }

    public static IdGenerator getEagerInstance() {
        return EAGER_INSTANCE;
    }

    public static synchronized IdGenerator getLazyInstance() {
        if (lazyInstance == null) {
            lazyInstance = new IdGenerator();
        }
        return lazyInstance;
    }

    public String nextId(String prefix) {
        return prefix + "-" + COUNTER.incrementAndGet();
    }
}

