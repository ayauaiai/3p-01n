import java.util.concurrent.ThreadLocalRandom;

/**
 * Part 1: The Phantom Bug
 *
 * Monte Carlo estimation of pi using 50,000,000 random points thrown into
 * a 1x1 square, split across 4 native Java threads.
 *
 * BUG (intentional): all 4 threads increment the SAME shared static field
 * `totalHits` via totalHits++ with NO synchronization. This is a classic
 * read-modify-write data race:
 *
 *      totalHits++   compiles to roughly:
 *          1. read totalHits into a register
 *          2. add 1
 *          3. write the result back to totalHits
 *
 * With 4 threads doing this concurrently and no lock/atomic/volatile-CAS,
 * steps from different threads interleave. Two threads can both read the
 * same value, both add 1, and both write back the same result -- one
 * increment is silently lost. Under heavy contention (4 threads hammering
 * one variable tens of millions of times) this happens constantly, so a
 * large fraction of the hits never get counted.
 *
 * Since pi is estimated as 4 * hits / total, and `total` (points thrown)
 * is NOT affected by the race (each thread only ever reads its own loop
 * counter), lost hits make the numerator shrink while the denominator
 * stays fixed at 50,000,000. The result is a systematically LOW and
 * run-to-run INCONSISTENT estimate of pi -- typically landing somewhere
 * around 1.8 to 2.4 instead of ~3.14159.
 */
public class PhantomBugPi {

    static final long TOTAL_POINTS = 50_000_000L;
    static final int NUM_THREADS = 4;
    static long totalHits = 0;

    public static void main(String[] args) throws InterruptedException {
        long pointsPerThread = TOTAL_POINTS / NUM_THREADS;

        Thread[] threads = new Thread[NUM_THREADS];

        long startTime = System.nanoTime();

        for (int t = 0; t < NUM_THREADS; t++) {
            threads[t] = new Thread(() -> {
                ThreadLocalRandom rnd = ThreadLocalRandom.current();
                for (long i = 0; i < pointsPerThread; i++) {
                    double x = rnd.nextDouble();
                    double y = rnd.nextDouble();
                    if (x * x + y * y <= 1.0) {
                        totalHits++;   // <-- UNSYNCHRONIZED, RACY INCREMENT
                    }
                }
            });
            threads[t].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long elapsedMs = (System.nanoTime() - startTime) / 1_000_000;

        double piEstimate = 4.0 * totalHits / TOTAL_POINTS;

        System.out.printf(
            "Points: %,d | Hits: %,d | Pi estimate: %.6f | Time: %d ms%n",
            TOTAL_POINTS, totalHits, piEstimate, elapsedMs
        );
    }
}
