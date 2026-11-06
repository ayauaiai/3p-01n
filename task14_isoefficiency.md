# Task 14. Isoefficiency and Gustafson Scaling

## Definitions

- W: useful work (in time units on one processor)
- T_o(W, P) = P·T_par − W: total overhead (idle time and communication)
- Efficiency: E = W / (W + T_o)
- Isoefficiency: to keep E constant, W = K · T_o(W, P), where K = E / (1 − E)

## Gustafson-compliant program

Runtime is fixed at T = 1 (s + p = 1), with s = α. Total processor-time is P·T = P. Useful work is W = s + P·p. Overhead is the idle time of the other P − 1 processors during the serial part:

```
T_o = P − (s + P·p) = P − α − P(1 − α) = α(P − 1)
```

Setting W = K · T_o gives W = K·α·(P − 1), so **W = Θ(P)**.

## Result

The isoefficiency function of a Gustafson-compliant program is **Θ(P)**, i.e. work must grow linearly with P. This is the best possible: using P processors needs at least P times more work. That is the sense in which Gustafson scaling is "ideal".

## Critique

The result depends on T_o being linear in P. With communication overhead:

| Overhead T_o | Isoefficiency |
|---|---|
| α·P (Gustafson) | Θ(P) |
| P · log P (tree collectives) | Θ(P log P) |
| P^(3/2) (e.g. √P per-processor cost) | Θ(P^(3/2)) |

Gustafson scaling is the baseline: the more overhead grows with P, the faster the problem must grow to keep the same efficiency.
