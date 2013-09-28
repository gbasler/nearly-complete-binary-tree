## Motivation

A lot of memory is wasted in Java containers, see e.g.,
[Memory-efficient Java Applications](http://www.cs.virginia.edu/kim/publicity/pldi09tutorials/memory-efficient-java-tutorial.pdf).

Very often data structures are build up in a setup-phase and never altered again. The computation intensive phase
starts after the data structures have been initialized.
This allows other representation
of data structures in memory that are otherwise inefficient.
Replacing e.g., [fastutil's](http://fastutil.di.unimi.it/docs/it/unimi/dsi/fastutil/doubles/Double2DoubleMap.html)
`Double2DoubleMap` with a simple array and binary search reduced the memory requirements down to 10%.

However, it's also possible to store a binary tree in an array in a memory-efficient manner.
The idea is simple: the tree is populated according to [BFS](http://faculty.cs.niu.edu/~mcmahon/CS241/Notes/bintree.html)
(the left child can be stored at subscript `2k+1` and the right child can be stored at subscript `2k+2`).
Such a tree is called _almost complete binary tree_ or _nearly complete binary tree_.

## Benchmark

A simple benchmark (searches ordered for each element in the tree once) shows that a nearly complete binary tree
is often faster than binary search (worst case complexity is the same):

```
::Benchmark Nearly complete binary tree.index of element::
Parameters(size -> 30000): 4.978
Parameters(size -> 330000): 86.389
Parameters(size -> 630000): 145.684
Parameters(size -> 930000): 225.129
Parameters(size -> 1230000): 329.36
Parameters(size -> 1530000): 391.122

::Benchmark Binary search.index of element::
Parameters(size -> 30000): 5.436
Parameters(size -> 330000): 87.801
Parameters(size -> 630000): 161.336
Parameters(size -> 930000): 242.394
Parameters(size -> 1230000): 362.468
Parameters(size -> 1530000): 419.529
```

### Howto Run

1. Install [SBT](http://www.scala-sbt.org/), put the launcher script in your path
1. Run `sbt`
1. Type `gi` to generate IntelliJ project files
1. Run the benchmarks with `bench`
1. You can run the unit tests with `test`