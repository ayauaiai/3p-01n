# Task 04. Asymptotic Analysis of Parallel Efficiency

## 1. Gustafson efficiency

```
E(P) = S(P)/P = [P − α(P − 1)] / P = (1 − α) + α/P
```

**Limit:** lim P→∞ E(P) = 1 − α.

**Proof.** |E(P) − (1 − α)| = α/P. For any ε > 0 choose P > α/ε; then the difference is below ε. ∎

**Properties.** E(1) = 1, and dE/dP = −α/P² < 0, so E decreases monotonically but stays above 1 − α for every finite P.

## 2. Amdahl efficiency

```
E_A(P) = S_A(P)/P = 1 / (s·P + 1 − s)
```

**Limit:** for s > 0 the denominator s·P + 1 − s → ∞, so lim P→∞ E_A(P) = 0, decaying like 1/(s·P). ∎

## 3. Comparison (α = s = 0.04)

| P | E Gustafson | E Amdahl |
|---|---|---|
| 16 | 0.9625 | 0.6250 |
| 64 | 0.9606 | 0.2841 |
| 256 | 0.9602 | 0.0893 |
| 1024 | 0.9600 | 0.0239 |
| 16384 | 0.9600 | 0.0015 |

## 4. Interpretation

- **Amdahl:** the problem is fixed, so work per processor shrinks as 1/P and the serial part dominates; efficiency collapses to 0.
- **Gustafson:** work per processor stays constant, so the idle time during the serial part is a constant fraction α of the runtime; efficiency settles at 1 − α.

With α = 0.04, a weakly scaled code keeps about 96% efficiency at any scale, while a fixed-size code with s = 0.04 uses only about 2% of 1024 processors.
