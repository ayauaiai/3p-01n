# Task 11. Gustafson vs. Sun-Ni Memory-Bounded Speedup

## Formulas

Let s be the serial fraction and G(P) the factor by which the parallel workload grows when memory grows P times.

```
Sun-Ni:  S*(P) = (s + (1 − s)·G(P)) / (s + (1 − s)·G(P)/P)
```

| G(P) | Result | Model |
|---|---|---|
| 1 | 1 / (s + (1 − s)/P) | Amdahl (fixed size) |
| P | s + (1 − s)·P | Gustafson (fixed time) |
| other | in between, or beyond | Sun-Ni |

So Sun-Ni generalizes both laws: Amdahl and Gustafson are special cases.

## Where Gustafson is wrong

- **G(P) > P (underestimates).** When work grows faster than memory, e.g. dense matrix multiply: memory ∝ N², work ∝ N³, so G(P) = P^(3/2). Example (s = 0.04, P = 64, G = 512): S* = 491.56 / 7.72 ≈ 63.7, versus Gustafson 61.5. (Runtime is no longer constant in this case.)
- **G(P) < P (overestimates).** When work grows slower than memory (data-heavy codes where extra memory adds little computation), Gustafson claims too much.
- **Communication growth.** If overhead grows with P, Gustafson overestimates, because it assumes α is constant (see Task 12).

## Summary

Gustafson assumes the workload grows exactly linearly with P. Sun-Ni lets it grow as memory allows, which is more realistic when memory, not time, is the limit.
