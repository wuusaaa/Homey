package managertests;

/**
 * Created by barakm on 20/03/2017.
 */

public final class TimeUtils {
    private TimeUtils() {
    }

    private static long timeout = 1500;

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
}
