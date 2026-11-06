# Task 13. When Gustafson Fails

Gustafson needs two things: runtime stays constant when the problem grows with P, and the serial part does not grow. Two algorithms break this.

## Case 1: Conjugate Gradient on a 3D Poisson grid

- Keep n = N³/P grid points per processor, so N ∝ P^(1/3).
- Unpreconditioned CG needs about N iterations (condition number grows as N²), so iterations grow as P^(1/3).
- Each iteration also has global dot products costing O(log P).

```
T(P) ∝ P^(1/3) · (t_comp + t_reduce · log P)
```

Going from P = 1 to 512, iterations alone make the run at least 8× longer. The algorithmic complexity changes with problem size, and global reductions add synchronization contention. The constant-time premise fails.

## Case 2: Parallel sorting (all-to-all exchange)

- Keep n elements per processor; total N = n·P.
- Local work is n·log(n·P), which grows with P.
- Redistribution is an all-to-all exchange. Its volume across the network's bisection grows with P, so links saturate.

Again runtime grows with P (log factor plus bandwidth contention), so fixed-time scaling does not hold.

## Why the premise breaks

In both cases, growing the problem changes the cost per unit of work (more iterations, a log factor, heavier communication), so work does not grow linearly with fixed time. The "serial" part also stops being constant: the reductions and exchanges grow with P.
