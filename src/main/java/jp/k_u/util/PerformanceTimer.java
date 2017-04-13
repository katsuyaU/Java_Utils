package jp.k_u.util;
public class PerformanceTimer {
    public static void run(Runnable run){// : (void -> void) -> void
        System.out.println("***start***");
        long start = System.currentTimeMillis();
        run.run();
        long stop = System.currentTimeMillis();
        System.out.println("***stop***");
        long d = (stop - start) ;
        System.out.println(d + "ns");
    }
}