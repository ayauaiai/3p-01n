# Task 07. Maximum Tolerable Serial Fraction

Given: P = 8192, required S(P) ≥ 7500.

## Derivation

```
S = P − α(P − 1) ≥ 7500
α(P − 1) ≤ P − 7500
α ≤ (P − 7500) / (P − 1)
```

## Calculation

```
α_max = (8192 − 7500) / (8192 − 1) = 692 / 8191 ≈ 0.08448
```

**α_max ≈ 0.0845 (about 8.45%).**

## Check

S = 8192 − 0.08448 · 8191 = 8192 − 692 = 7500 ✓

Any α below 8.45% gives S > 7500. The matching efficiency is E = 7500 / 8192 ≈ 91.6%.
