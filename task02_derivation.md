# Task 02. Deriving Gustafson's Law from First Principles

## 1. Definitions
- \(P\) – number of processors.
- \(s\) – time spent on sequential work.
- \(p\) – time spent on parallel work.
- \(\alpha\) – sequential fraction of parallel execution.

## 2. Step 1: Normalization

We assume that total parallel execution time is 1: s+p=1
Therefore:
s=\alpha,\qquad p=1-\alpha

## 3. Step 2: Work Done by P Processors

- Sequential work: one processor performs work \(s\).
- Parallel work: all \(P\) processors work together. Total work is \(P\cdot p\).

## 4. Step 3: Execution on One Processor

One processor must complete all the work sequentially:T_{serial}(P)=s+P\cdot p


## 5. Step 4: Gustafson's Speedup Formula

Speedup is the serial execution time divided by parallel execution time:S(P)=\frac{s+P\cdot p}{s+p}


Since \(s+p=1\): boxed{S(P)=s+P\cdot p}


Substituting \(s=\alpha\) and \(p=1-\alpha\): boxed{S(P)=P-\alpha(P-1)}


This is Gustafson's Law.

## 6. Step 5: Special Cases

| Condition | Result | Meaning |
|---|---|---|
| \(\alpha=0\) | \(S=P\) | Perfect parallelization |
| \(\alpha=1\) | \(S=1\) | No speedup |
| \(P=1\) | \(S=1\) | One processor |

## 7. Step 6: Geometric Interpretation

Gustafson's formula is a straight line with slope \(1-\alpha\).
Each additional processor increases speedup by approximately \(1-\alpha\).
Unlike Amdahl's Law, speedup can continue growing as more processors are added.

## 8. Step 7: Numerical Example

Given:\alpha=0.04,\qquad P=1024
S=1024-0.04(1023)
\boxed{S=983.08}
Both formula forms give the same result.

## 9. Assumptions

1. Work is perfectly distributed between processors.
2. Communication and synchronization overheads are ignored or included in sequential time.
3. The problem size can increase with the number of processors.
4. One processor can theoretically complete the entire enlarged problem.

## Conclusion

Gustafson's Law shows that adding processors allows us to solve larger problems in approximately the same time. Its speedup depends on the sequential part and the number of processors.