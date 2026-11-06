# Task 05. Deconstructing the "Amdahl's Pessimism" Fallacy: the nCUBE 10 Case

> Figures from the 1988 paper are quoted from memory and rounded; verify against the sources listed at the end.

## 1. Background

By the 1980s, Amdahl's Law (1967) was widely read as proof that massive parallelism is pointless: with even 1% serial work, speedup can never exceed 100×. In 1988 Gustafson, Montry and Benner (Sandia National Laboratories) reported near-linear speedups on a 1,024-processor nCUBE/10 hypercube, which seemed impossible under that reading.

## 2. The experiment

- **Machine:** nCUBE/10, 1,024 nodes, hypercube topology, small distributed memory per node.
- **Applications:** beam strain analysis, surface wave simulation, unstable fluid flow.
- **Result:** scaled speedups of roughly 1016-1021 out of 1024 (about 99% efficiency).
- **Method:** each node kept a fixed amount of work, and the total problem grew with the number of processors.

## 3. Why it looked impossible

With a serial fraction s = 0.4% = 0.004:

```
Amdahl ceiling: 1/s = 250
Amdahl at P = 1024: 1 / (0.004 + 0.996/1024) ≈ 201
```

A speedup of 1000 on 1024 processors would require s ≤ (1/1000 − 1/1024) / (1 − 1/1024) ≈ 2.3·10⁻⁵, about 170 times smaller.

## 4. How problem expansion removes the ceiling

Serial work (setup, I/O) barely grows with problem size, while the parallel work (the grid computation) grows with the number of processors. Using Gustafson's formula with α measured on the 1,024-processor run:

```
S = 1024 − 1023·α
α = 0.004 → S ≈ 1019.9
α = 0.008 → S ≈ 1015.8
```

This matches the observed range. The same 0.4% that caps Amdahl at ≈ 201 gives ≈ 1020 under scaling. On a single processor, the serial share of the big problem is only s₁ = α / (α + (1 − α)·P) ≈ 3.9·10⁻⁶, so its Amdahl ceiling (≈ 255,000) never binds.

## 5. Caveat

One nCUBE node had too little memory to run the full problem, so T_serial was estimated from the parallel run rather than measured. This is why scaled speedup was later refined (e.g. Sun-Ni, Task 11).

## 6. Conclusion

Amdahl's Law is correct for a fixed problem; the fallacy was applying its ceiling to cases where the problem grows with the machine. Users scale the problem to the machine, so weak scaling reflects how large systems are actually used.

## Sources

- Gustafson, Montry, Benner, "Development of Parallel Methods for a 1024-Processor Hypercube," SIAM J. Sci. Stat. Comput., 9(4), 1988.
- Gustafson, "Reevaluating Amdahl's Law," Communications of the ACM, 31(5), 1988.
- Amdahl, "Validity of the single processor approach to achieving large scale computing capabilities," AFIPS, 1967.
