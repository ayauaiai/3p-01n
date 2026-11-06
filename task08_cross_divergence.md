# Task 08. Amdahl vs. Gustafson (s = α = 0.08, P = 128)

## Amdahl

```
S_A = 1 / (0.08 + 0.92/128) = 1 / (0.08 + 0.0071875) = 1 / 0.0871875 ≈ 11.47
```

## Gustafson

```
S_G = 128 − 0.08 · 127 = 128 − 10.16 = 117.84
```

## Discrepancy

| Measure | Value |
|---|---|
| Ratio S_G / S_A | 10.27× |
| Amdahl is lower than Gustafson by | (117.84 − 11.47) / 117.84 ≈ 90.3% |
| Gustafson is higher than Amdahl by | (117.84 − 11.47) / 11.47 ≈ 927% |

## Why they diverge

Same number (0.08), different meaning. In Amdahl the problem is fixed, so the serial part is 8% of a fixed amount of work and caps speedup at 1/0.08 = 12.5. In Gustafson the problem grows with P, so the serial part stays the same absolute size while the parallel work grows, and speedup keeps rising almost linearly. As P grows the gap widens: Amdahl flattens toward 12.5, Gustafson keeps growing with slope 0.92.
