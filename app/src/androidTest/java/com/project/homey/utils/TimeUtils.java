package com.project.homey.utils;

import android.support.test.espresso.core.deps.guava.base.Supplier;

import org.hamcrest.Matcher;

import java.util.function.Predicate;

/**
 * Created by barakm on 20/03/2017
 */

public final class TimeUtils {
    private TimeUtils() {
    }

    private static long timeout = 1500;
    private static final long interval = 200;
    private static Predicate<Object> predicate;

    public static void Wait() {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void Wait(long newTimeOut) {
        timeout = newTimeOut;
        Wait();
    }

    public static <V> void busyWait(long timeout, Matcher<V> expected, Supplier<V> actual) throws InterruptedException {
        int elapsed = 0;
        while (!expected.matches(actual.get()) && elapsed < timeout) {
            TimeUtils.Wait(interval);
            elapsed += interval;
        }

        if (elapsed >= timeout) {
            throw new InterruptedException("Busy wait didn't succeeded");
        }
    }
}
