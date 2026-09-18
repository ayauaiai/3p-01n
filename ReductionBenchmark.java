import java.util.concurrent.ThreadLocalRandom;

/**
 * Part 3: OpenMP-Style Reduction
 *
 * Each thread accumulates hits into its OWN private local variable
 * (a plain local `long hits` inside the thread's run loop -- never shared,
 * never touched by another thread). Only after all threads finish does the
 * main thread sum the partial results. This is the Java equivalent of:
 *
 *     #pragma omp parallel for reduction(+:totalHits)
 *
 * No lock, no CAS, no shared-memory writes during the hot loop at all --
 * each thread's counter lives in its own stack frame / register the whole
 * time, so there is zero contention until the single, cheap, one-time sum
 * at join().
 *
 * Deliverable: benchmark T in {1, 2, 4, 8, 16, 32} threads with
 * 100,000,000 total iterations, and record runtime, speedup (T1 / TN),
 * and efficiency (Speedup / T).
 */
public class ReductionBenchmark {

    static final long TOTAL_POINTS = 100_000_000L;
    static final int[] THREAD_COUNTS = {1, 2, 4, 8, 16, 32};

    public static void main(String[] args) throws InterruptedException {
        System.out.println("== Part 3: OpenMP-Style Reduction ==");
        System.out.printf("Total points: %,d%n%n", TOTAL_POINTS);

        long[] runtimes = new long[THREAD_COUNTS.length];

        for (int idx = 0; idx < THREAD_COUNTS.length; idx++) {
            int t = THREAD_COUNTS[idx];
            long[] result = runWithThreads(t); // {elapsedMs, hits}
            runtimes[idx] = result[0];
            double pi = 4.0 * result[1] / TOTAL_POINTS;
            System.out.printf("T=%-3d  Hits: %,12d  Pi: %.6f  Time: %,6d ms%n",
                    t, result[1], pi, result[0]);
        }

        long baseline = runtimes[0];

        System.out.println();
        System.out.println("---- Results Grid ----");
        System.out.printf("%-10s %-14s %-20s %-12s%n", "Threads", "Runtime(ms)", "Speedup(T1/TN)", "Efficiency");
        for (int idx = 0; idx < THREAD_COUNTS.length; idx++) {
            int t = THREAD_COUNTS[idx];
            double speedup = baseline / (double) runtimes[idx];
            double efficiency = (speedup / t) * 100.0;
            System.out.printf("%-10d %-14d %-20.2f %-12.1f%%%n",
                    t, runtimes[idx], speedup, efficiency);
        }
    }

    /** Runs the point-in-circle test with `numThreads` threads, each keeping
     *  a private local counter, summed once at the end. Returns {elapsedMs, totalHits}. */
    static long[] runWithThreads(int numThreads) throws InterruptedException {
        long pointsPerThread = TOTAL_POINTS / numThreads;
        long remainder = TOTAL_POINTS % numThreads; // give leftover points to last thread

        Thread[] threads = new Thread[numThreads];
        final long[] partialHits = new long[numThreads]; // one slot per thread, written once each

        long start = System.nanoTime();

        for (int t = 0; t < numThreads; t++) {
            final int threadIdx = t;
            final long iterations = pointsPerThread + (t == numThreads - 1 ? remainder : 0);
            threads[t] = new Thread(() -> {
                long localHits = 0; // PRIVATE counter -- lives in this thread only
                ThreadLocalRandom rnd = ThreadLocalRandom.current();
                for (long i = 0; i < iterations; i++) {
                    double x = rnd.nextDouble();
                    double y = rnd.nextDouble();
                    if (x * x + y * y <= 1.0) {
                        localHits++;
                    }
                }
                partialHits[threadIdx] = localHits; // single write at the very end
            });
            threads[t].start();
        }

        for (Thread thread : threads) thread.join();

        long totalHits = 0;
        for (long h : partialHits) totalHits += h; // reduction step, done once, single-threaded

        long elapsed = (System.nanoTime() - start) / 1_000_000;
        return new long[]{elapsed, totalHits};
    }
}
