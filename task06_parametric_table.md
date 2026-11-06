# Task 06. Parametric Speedup Across Processor Counts (α = 0.04)

Formulas: S(P) = P − α(P − 1), E(P) = S(P) / P.

Example (P = 16): S = 16 − 0.04·15 = 15.4, E = 15.4 / 16 = 0.9625.

| P | S(P) | E(P) |
|---|---|---|
| 16 | 15.40 | 0.9625 |
| 64 | 61.48 | 0.9606 |
| 256 | 245.80 | 0.9602 |
| 1024 | 983.08 | 0.9600 |
| 4096 | 3932.20 | 0.9600 |
| 16384 | 15728.68 | 0.9600 |

## Evaluation

Efficiency barely degrades as P grows exponentially: it falls from 96.25% to 96.00% and flattens at 1 − α = 96%, because E(P) = (1 − α) + α/P and the α/P term vanishes. Speedup grows almost linearly, with slope 1 − α = 0.96 per added core.

## Check script

```python
a = 0.04
for P in [16, 64, 256, 1024, 4096, 16384]:
    S = P - a * (P - 1)
    print(P, round(S, 2), round(S / P, 4))
```
