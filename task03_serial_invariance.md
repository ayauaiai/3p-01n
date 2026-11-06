# Task 03. The Serial Fraction Invariance Paradox

**Question.** Amdahl measures the serial fraction s on one processor; Gustafson-Barsis measures α on the parallel machine. Why does measuring on the parallel machine remove the apparent contradiction with massive scaling?

## 1. Two frames of reference

Let the program have a serial part of absolute time **a** (input, setup, final output; independent of problem size) and a parallel part that takes time **b** on one processor at base problem size.

**Amdahl's frame (1 processor, fixed problem):** s = a / (a + b). The problem never grows, so s is constant and S → 1/s.

**Gustafson's frame (P processors, scaled problem):** the problem is scaled so the parallel part takes time b on P processors (its work grows P times, as one processor would need P·b). Then:

- parallel runtime: T_par = a + b, so α = a / (a + b)
- one processor would need: T_ser = a + P·b

## 2. Linking the two fractions

The serial fraction of the *scaled* problem, measured on one processor, is s₁ = a / (a + P·b). Writing it through α:

```
s₁ = α / (α + (1 − α)·P)
```

For fixed α, s₁ → 0 like 1/P. The same serial time a looks like a constant α on the parallel machine, but like a vanishing fraction on a single processor running the big problem.

## 3. Amdahl applied to the scaled problem equals Gustafson

```
s₁ + (1 − s₁)/P = (a + b) / (a + P·b)
S = (a + P·b) / (a + b) = α + (1 − α)·P = P − α(P − 1)
```

So the two laws are consistent. They differ only in what is held fixed: Amdahl fixes the fraction s (problem size fixed); Gustafson fixes the absolute serial time a (the problem grows).

## 4. Numeric check (α = 0.04, P = 1024)

```
s₁ = 0.04 / (0.04 + 0.96·1024) = 0.04 / 983.08 ≈ 4.07e-5
S  = 1024 − 0.04·1023 = 983.08
```

A 4% serial share of the parallel run is only 0.004% of the single-processor run, so Amdahl's ceiling for the scaled problem (1/s₁ ≈ 24,600) is far above 1024.

## 5. Why measuring on the parallel machine resolves the paradox

- α is observed directly in the large run, where the real idle time of the other P − 1 processors occurs.
- The large problem often cannot run on one processor (memory, time), so s₀ or s₁ can only be estimated, never measured.
- α already accounts for problem growth, so it plugs straight into a linear formula with no 1/α ceiling.

## 6. Critique

The argument assumes a does not grow with problem size or P. If it does (e.g. I/O proportional to data, reductions growing as log P), α is no longer constant and the advantage shrinks (see Task 12). α should therefore be measured on a real run at the target scale.
