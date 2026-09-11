# 3p-01n
Task 1 — Trace the Code (5 min)
Determine the final output. Show the value of x and y after each statement.
x = 5
y = 2
x = x + y
y = x * 2
x = y - x
print(x, y)

Statement	x	y
x = 5	y = 2	  
y = 2	x =	7
y = 14	x = 7	
x = 7 y =	14

Task 2 — Find the Bug (5 min)
What does this program actually calculate? What was it probably intended to calculate? Fix it.
_this program calculate_the last element of the list, 50__________________________________________________________________________
_this program probably meant to calculate the sum of all elements__________________________________________________________________________
__total = numbers[i] to total += numbers[i].__________________________________________________________________________
numbers = [10, 20, 30, 40, 50]
total = 0
for i in range(len(numbers)):
total = numbers[i]
print(total)

Task 3 — Nested Loops (5 min)
For the program below: (a) how many times does print() execute? (b) what if both loops run from 0 to 999? (c)
what if the outer loop runs 10 times and the inner loop runs 1,000,000 times?
_10*10 = 100 prints
1000 * 1000 = 1000000___________________________________________________________________________
__10 * 1 000 000 = 10000000__________________________________________________________________________
____________________________________________________________________________
for i in range(10):
for j in range(10):
print(i, j)

Part B — Algorithms & Complexity
Task 4 — Which Is Faster? (5 min)
Algorithm A scans an array once. Algorithm B compares every element with every element. For an array of size n,
estimate the amount of work for each. Which grows faster? What happens when n = 1,000,000?
Algorithm A scans an array once _O(n)_________________________________________________________
Algorithm B compare every pair O(n²).__________________________________________________________________________
_B grows much faster than A___________________________________________________________________________
____________________________________________________________________________
Task 5 — Complexity Ranking (7 min)
Rank these from fastest-growing to slowest-growing as n increases:
n² log₂ n n n log₂ n 1 2ⁿ
Which become particularly problematic when n becomes much larger?

2ⁿ  >  n²  >  n log₂ n  >  n  >  log₂ n  >  1
2ⁿ and n² become problematic fastest as n grows

Task 6 — Searching (7 min)
Given [3, 8, 12, 17, 24, 31, 45, 51, 63],
find 45 using two approaches:
(1) if you do not know the list is sorted;
(2) if you know it is sorted. Explain why the second can be faster.
Unsorted assumption: linear search — check each element in order until found. O(n), worst case checks all 9 elements.
Known sorted: binary search — compare with the middle element, discard the half that can't contain the target, repeat. O(log n), needs at most ~4 comparisons for 9 elements.
Why faster: binary search eliminates half the remaining candidates with every comparison instead of just one element, so the number of comparisons grows logarithmically instead of linearly.

Part C — Data & Problem Solving
Task 7 — Large Dataset (8 min)
You have 500 million integers and need to find the largest number. Design an algorithm. How many times does
each number need to be examined? Do you need to store all 500 million numbers? Could you process the data in
chunks?
Keep a running variable max_val.
Stream through the numbers once; for each one, if it's greater than max_val, update it.
Each number is examined exactly once — O(n).
No need to store all 500 million numbers in memory at once.
Yes, can process in chunks: read a chunk, scan it for its local max, discard the chunk, compare local max to the running global max, repeat.

Task 8 — Counting (5 min)
Given A B A C B A D C A B, design an algorithm producing A→4, B→3, C→2, D→1. What data structure would
you use?

hash map

Task 9 — Matrix Operations (5 min)
A = [[1,2],[3,4]] and B = [[5,6],[7,8]]. Calculate A+B and A×B. How many individual multiplication operations are
required for two 2×2 matrices?
A = [[1,2],[3,4]]   B = [[5,6],[7,8]]

A + B = [[6, 8], [10, 12]]

A × B = [[1*5+2*7, 1*6+2*8],
         [3*5+4*7, 3*6+4*8]]
      = [[19, 22], [43, 50]]

      
Part D — Systems Intuition
Task 10 — CPU vs Memory
python
for x in huge_array:
    total += x

Three possible reasons it's slow despite a fast CPU:

Cache misses: the array doesn't fit in cache, so the CPU frequently waits on slower main memory (RAM).
Memory bandwidth limits: even sequential access can be bottlenecked by how fast data can be moved from RAM to the CPU.
I/O or single-threaded overhead: the data may be loaded from disk/network, or the language/runtime adds per-iteration overhead (e.g. interpreted loop), and only one core is being used.
