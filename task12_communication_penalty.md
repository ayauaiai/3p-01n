# Task 12. Gustafson with Communication Overhead

## Model

Keep the normalization s + p = 1 for compute, and add a communication term C(P), measured in the same time units. Parallel runtime becomes 1 + C(P). One processor needs no communication, so its time is still s + P·p.

```
S_C(P) = (s + P·p) / (1 + C(P)) = [P − α(P − 1)] / (1 + C(P))
E_C(P) = E_G(P) / (1 + C(P))
```

With C = 0 this is the original law.

## Choices of C(P)

| C(P) | Behavior as P → ∞ |
|---|---|
| constant c | E → (1 − α) / (1 + c) (still positive) |
| c · log₂ P (trees, collectives) | E ≈ (1 − α) / (c · log₂ P) → 0 slowly |
| c · √P (2D mesh networks) | E ≈ (1 − α) / (c · √P) → 0 faster |

## Example (α = 0.04, P = 1024, S_G = 983.08)

- C = 0.01·log₂ P = 0.10: S = 983.08 / 1.10 ≈ 893.7, E ≈ 0.873
- C = 0.01·√P = 0.32: S = 983.08 / 1.32 ≈ 744.8, E ≈ 0.727

(The coefficient 0.01 is an assumed example value.)

## Conclusion

Weak scaling stays near-ideal only if communication per step stays bounded as P grows. If C(P) grows, efficiency eventually falls to zero, no matter how small α is.
