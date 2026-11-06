# Task 15. Energy Under Scaled Speedup

## Model

- P_idle: static power of a node that is switched on (always paid).
- P_active: extra dynamic power while a core computes.
- Wall-clock time T = 1 (s + p = 1), so the machine does S(P) = s + P·p units of work.

During the serial part (time s) one core is active; during the parallel part (time p) all P are active. Active core-time = s + P·p = S(P).

## Energy on P cores

```
E_P = P · P_idle · T + P_active · S(P)
```

## Energy on 1 core (same wall-clock time)

In the same time T, one core finishes only 1 unit of work (the unscaled problem):

```
E_1 = (P_idle + P_active) · T
```

## Energy per unit of work

```
1 core:   P_idle + P_active
P cores:  E_P / S(P) = P_active + P_idle / E(P)      (E(P) = S/P is the efficiency)
```

Since E(P) ≤ 1, the P-core system pays a small extra idle cost, P_idle · (1/E − 1).

## Example (assumed values: P_idle = 50 W, P_active = 100 W; α = 0.04, P = 1024)

- E_P = 1024·50 + 100·983.08 = 149,508 J, which is about 152.1 J per unit of work
- 1 core: 150 J per unit of work
- Difference: about 1.4% more energy per unit of work on 1024 cores, in exchange for 983× more work in the same time.

## Conclusion

Total energy grows about P times (more hardware, more work), but energy per unit of work stays nearly constant when efficiency is high. It rises as efficiency drops, because idle nodes still burn P_idle.
