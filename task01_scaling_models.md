# Task 01. Strong Scaling vs. Weak Scaling: Amdahl's Law and Gustafson's Law
# 1. Basic Assumptions
# Amdahl's Law (1967) – Fixed-Size Scaling

Amdahl's Law focuses on solving the same problem faster by adding more processors.The total amount of work remains constant, even when the number of processors increases.

The main assumptions are:
- The problem size \(W\) is fixed.
- The sequential part \(s\) does not change.
- The parallel part can be divided between \(P\) processors.

The execution time is: T(P)=s+\frac{1-s}{P}

The speedup is: S_{Amdahl}(P)=\frac{1}{s+\frac{1-s}{P}}


When the number of processors approaches infinity: \lim_{P\to\infty}S_{Amdahl}(P)=\frac{1}{s}
Example: If the sequential part is 5% (\(s=0.05\)), the maximum speedup is: S_{max}=\frac{1}{0.05}=20
This means that even with thousands of processors, the speedup cannot exceed 20 times.
Main idea: Adding more processors cannot remove the limitation caused by sequential work.

### Gustafson's Law (1988) – Fixed-Time Scaling

Gustafson's Law focuses on solving **larger problems in the same amount of time**.

Instead of keeping the problem size fixed, we increase the problem size when we add more processors.

The main assumptions are:

- The execution time remains constant.
- The sequential part stays approximately the same.
- The parallel part increases with the number of processors.
- Each processor continues to perform a useful amount of work.

The speedup formula is: S_{Gustafson}(P)=s+pP
Since \(s+p=1\), it can also be written as: S_{Gustafson}(P)=P-\alpha(P-1)
Here, \(\alpha\) represents the sequential fraction of the execution time on the parallel system.
**Main idea:** More processors allow us to solve larger and more complex problems within the same time limit.

---

## 2. How Does Problem Size Change When P Increases?

The two laws behave differently as the number of processors approaches infinity.

| Model | Problem Size | Execution Time | What Changes? |
|---|---|---|---|
| Amdahl | \(N(P)=N_0\) (constant) | Decreases and approaches \(s\) | More processors work on the same problem. |
| Gustafson | \(N(P)\rightarrow\infty\) | Remains constant | More processors solve a larger problem. |

### Example: A 3D Grid

Imagine a scientific simulation where the amount of work grows as: W(N)=O(N^3)
Under Gustafson's Law, the parallel workload grows proportionally to the number of processors.
Therefore: N(P)\propto P^{1/3}
This means that when we add more processors, we can increase the grid size while keeping execution time approximately constant.
Under Amdahl's Law, the grid size does not change.

### Work Per Processor

- **Amdahl:** As more processors are added, each processor receives less work. Eventually, the sequential part becomes the main limitation.
- **Gustafson:** The problem grows with the number of processors, so each processor can continue performing useful work.

---

## 3. Why Are They Called Fixed-Size and Fixed-Time Scaling?

### Amdahl = Fixed-Size

Amdahl's Law is called fixed-size scaling because the total problem size stays the same.

We only increase the number of processors.

The main question is:

How much faster can we solve the same problem?

The result is a shorter execution time, but the speedup has a theoretical limit.

### Gustafson = Fixed-Time

Gustafson's Law is called fixed-time scaling because the execution time stays approximately constant.

When we add more processors, we increase the problem size.

The main question is:

How much more work can we complete in the same amount of time?

The result is the ability to solve larger problems without increasing execution time significantly.

---

## 4. Philosophical and Physical Differences

### 1. Different Goals

Amdahl asks:

"How quickly can I solve this specific problem?"

Gustafson asks:

"How much larger of a problem can I solve in the same amount of time?"

In scientific research, such as weather forecasting, physics simulations and molecular dynamics, researchers often want to create more detailed and accurate models rather than simply solve the same problem faster.

### 2. What Remains Constant?

In Amdahl's Law, the problem size remains constant.

In Gustafson's Law, the execution time and absolute sequential work are assumed to remain approximately constant, while parallel work increases.

As a result, the sequential part becomes a smaller percentage of the total work.

### 3. Different Predictions

Amdahl's Law is often considered pessimistic because it gives a fixed maximum speedup of \(1/s\).

Gustafson's Law is more optimistic because speedup can grow almost linearly with the number of processors.

Its efficiency approaches: E(P)\rightarrow 1-\alpha


Therefore, efficiency can remain high even when the number of processors increases.

### 4. Physical Explanation

Modern parallel computers often have distributed memory.

When we add more computing nodes, we also gain more memory and computing power.

This allows researchers to increase the problem size instead of only making the same problem run faster.

This is one of the physical reasons why Gustafson's Law is important in modern scientific computing.

### 5. Limitations

Both laws are simplified models.

**Amdahl's Law:**
- Assumes a fixed problem size.
- Does not remove the sequential part.
- Does not fully consider communication and coordination overhead.

**Gustafson's Law:**
- Assumes that the sequential part stays approximately constant.
- Assumes that parallel work can grow with the number of processors.
- Does not fully consider communication costs and other overheads.

The two laws do not contradict each other. They describe two different ways of using parallel computers.

---

## 5. Final Comparison

| Feature | Amdahl's Law | Gustafson's Law |
|---|---|---|
| Scaling type | Strong scaling | Weak scaling |
| Main assumption | Fixed problem size | Fixed execution time |
| What increases? | Number of processors | Processors and problem size |
| Main goal | Solve the same problem faster | Solve a larger problem in the same time |
| Speedup | Has a maximum limit | Can grow almost linearly |
| Limiting factor | Sequential work | Sequential fraction and overhead |
| Typical use | Speeding up an existing task | Large scientific simulations |

## Conclusion

Amdahl's Law and Gustafson's Law describe two different approaches to parallel computing.

Amdahl's Law shows that the sequential part limits the maximum speedup when the problem size stays fixed.

Gustafson's Law shows that adding more processors allows us to solve larger problems within the same amount of time.

**The main difference is simple: Amdahl makes the same problem faster, while Gustafson makes it possible to solve bigger problems in the same time.**