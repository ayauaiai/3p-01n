import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Part 2: The Synchronization Trap
 *
 * Fixes the Part 1 data race two ways and times both against a plain
 * single-threaded baseline:
 *
 *   1. synchronized increment  - every totalHits++ is wrapped in a
 *      synchronized block, forcing all 4 threads to take a lock on every
 *      single hit.
 *   2. AtomicLong.incrementAndGet() - lock-free but still forces a
 *      hardware-level compare-and-swap (with a memory fence) on every hit,
 *      so all 4 cores still have to fight over the same cache line.
 *
 * The Lesson: both are now numerically CORRECT (pi ~ 3.1415, repeatable),
 * but both can end up slower than the naive single-threaded loop, because
 * every hit forces cross-core cache-line traffic ("the shared variable's
 * cache line has to bounce between cores' L1 caches on every write -
 * MESI/MOESI invalidation traffic"). With ~78.5% of 12,500,000 points per
 * thread landing inside the circle, that's tens of millions of lock
 * acquisitions / CAS operations contended across 4 cores.
 */
public class SynchronizationTrap {

    static final long TOTAL_POINTS = 50_000_000L;
    static final int NUM_THREADS = 4;

    static long syncHits = 0;
    static final Object lock = new Object();
    static AtomicLong atomicHits = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("== Part 2: The Synchronization Trap ==");
        System.out.printf("Total points: %,d | Threads: %d%n%n", TOTAL_POINTS, NUM_THREADS);

        long t1 = runSingleThreaded();
        long t2 = runSynchronized();
        long t3 = runAtomic();

        System.out.println();
        System.out.println("---- Summary ----");
        System.out.printf("Single-threaded loop : %6d ms%n", t1);
        System.out.printf("4 threads, synchronized: %6d ms  (%.2fx vs single-thread)%n",
                t2, t1 / (double) t2);
        System.out.printf("4 threads, AtomicLong : %6d ms  (%.2fx vs single-thread)%n",
                t3, t1 / (double) t3);
    }

    // ---- Baseline: plain single-threaded loop, no threading at all ----
    static long runSingleThreaded() {
        long hits = 0;
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        long start = System.nanoTime();
        for (long i = 0; i < TOTAL_POINTS; i++) {
            double x = rnd.nextDouble();
            double y = rnd.nextDouble();
            if (x * x + y * y <= 1.0) hits++;
        }
        long elapsed = (System.nanoTime() - start) / 1_000_000;
        double pi = 4.0 * hits / TOTAL_POINTS;
        System.out.printf("[Single-thread]   Hits: %,d | Pi: %.6f | Time: %,d ms%n", hits, pi, elapsed);
        return elapsed;
    }

    // ---- Fix #1: synchronized block around every increment ----
    static long runSynchronized() throws InterruptedException {
        syncHits = 0;
        long pointsPerThread = TOTAL_POINTS / NUM_THREADS;
        Thread[] threads = new Thread[NUM_THREADS];
        long start = System.nanoTime();
        for (int t = 0; t < NUM_THREADS; t++) {
            threads[t] = new Thread(() -> {
                ThreadLocalRandom rnd = ThreadLocalRandom.current();
                for (long i = 0; i < pointsPerThread; i++) {
                    double x = rnd.nextDouble();
                    double y = rnd.nextDouble();
                    if (x * x + y * y <= 1.0) {
                        synchronized (lock) {
                            syncHits++;
                        }
                    }
                }
            });
            threads[t].start();
        }
        for (Thread thread : threads) thread.join();
        long elapsed = (System.nanoTime() - start) / 1_000_000;
        double pi = 4.0 * syncHits / TOTAL_POINTS;
        System.out.printf("[synchronized]    Hits: %,d | Pi: %.6f | Time: %,d ms%n", syncHits, pi, elapsed);
        return elapsed;
    }

    // ---- Fix #2: AtomicLong.incrementAndGet() ----
    static long runAtomic() throws InterruptedException {
        atomicHits.set(0);
        long pointsPerThread = TOTAL_POINTS / NUM_THREADS;
        Thread[] threads = new Thread[NUM_THREADS];
        long start = System.nanoTime();
        for (int t = 0; t < NUM_THREADS; t++) {
            threads[t] = new Thread(() -> {
                ThreadLocalRandom rnd = ThreadLocalRandom.current();
                for (long i = 0; i < pointsPerThread; i++) {
                    double x = rnd.nextDouble();
                    double y = rnd.nextDouble();
                    if (x * x + y * y <= 1.0) {
                        atomicHits.incrementAndGet();
                    }
                }
            });
            threads[t].start();
        }
        for (Thread thread : threads) thread.join();
        long elapsed = (System.nanoTime() - start) / 1_000_000;
        double pi = 4.0 * atomicHits.get() / TOTAL_POINTS;
        System.out.printf("[AtomicLong]      Hits: %,d | Pi: %.6f | Time: %,d ms%n", atomicHits.get(), pi, elapsed);
        return elapsed;
    }
}
