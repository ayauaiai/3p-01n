# Task 10. Memory Growth vs. Scaled Speedup

## Assumptions

- Cap: 32 GB per node (1 GB = 10⁹ bytes), so total memory M(P) = 32·P GB (this is M = c·P with c = 32 GB).
- One grid array of double-precision values: 8 bytes per grid point. (If the solver keeps k arrays, divide N³ by k.)

## Formulation

```
N³ · 8 bytes ≤ P · 32·10⁹ bytes
N ≤ (P · 4·10⁹)^(1/3)
```

So N grows as P^(1/3): to double N you need 8× more nodes.

## Results

| Nodes P | Max N³ | Max N (per side) |
|---|---|---|
| 1 | 4.0·10⁹ | ≈ 1,587 |
| 64 | 2.56·10¹¹ | ≈ 6,349 |
| 512 | 2.048·10¹² | ≈ 12,699 |

Check: going from 64 to 512 nodes (8×) doubles N (6,349 → 12,699), as expected from P^(1/3).

## Takeaway

Memory scales linearly with P, so total grid points N³ can grow linearly with P (weak scaling works), but the grid side N only grows as P^(1/3).
