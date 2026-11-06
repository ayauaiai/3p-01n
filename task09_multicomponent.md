# Task 09. Multi-Component Workload

| Phase | Description | Share of parallel runtime | Type |
|---|---|---|---|
| A | Disk I/O + parameter broadcast | 2% | serial |
| B | Boundary synchronization | 6% | serial |
| C | Core flux computation | 92% | parallel |

## Aggregate serial fraction

```
α = 0.02 + 0.06 = 0.08
```

(Phases A and B are treated as non-scaling, so they count as serial.)

## Scaled speedup, S = P − α(P − 1)

```
P = 512:  S = 512 − 0.08 · 511 = 512 − 40.88 = 471.12     E = 0.9202
P = 2048: S = 2048 − 0.08 · 2047 = 2048 − 163.76 = 1884.24  E = 0.9200
```

## Note

Efficiency stays at about 92% (= 1 − α), so going from 512 to 2048 cores gives almost 4× more speedup. Cutting phase B (the largest serial piece) is the best way to improve: removing it entirely would give α = 0.02 and about 98% efficiency.
