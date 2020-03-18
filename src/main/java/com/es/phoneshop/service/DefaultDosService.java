package com.es.phoneshop.service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultDosService implements DosService {

    public static final int THRESHOLD = 20;
    public static final int DEFAULT_COUNT_VALUE = 0;

    private static volatile DefaultDosService instance;

    private volatile Date lastResetDate;
    private Map<String, AtomicInteger> ipCallCount;

    private DefaultDosService() {
        lastResetDate = new Date();
        ipCallCount = new ConcurrentHashMap<>();
    }

    public static DefaultDosService getInstance() {
        DefaultDosService localeInstance = instance;

        if (localeInstance == null) {
            synchronized (DefaultDosService.class) {
                localeInstance = instance;

                if (localeInstance == null) {
                    instance = new DefaultDosService();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean isAllowed(String ip) {
        this.checkPossibleReset();
        AtomicInteger count = ipCallCount.get(ip);

        if (count == null) {
            count = new AtomicInteger(DEFAULT_COUNT_VALUE);
            ipCallCount.put(ip, count);
        }
        final int value = count.incrementAndGet();

        return value < THRESHOLD;
    }

    private void checkPossibleReset() {
        Date date = new Date();
        if ((date.getTime() - lastResetDate.getTime()) > 60 * 1000) {
            lastResetDate = date;
            ipCallCount.clear();
        }
    }
}
