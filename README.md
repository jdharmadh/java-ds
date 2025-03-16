# Data Structures!

Some Java implementations of fundamental data structures and algorithms. This repository serves as both a reference and a learning resource for concepts I'm interested in.
### Graph
- `DirectedGraph.java`
- `UndirectedGraph.java` 
- `UndirectedWeightedGraph.java`
- `WeightedDirectedGraph.java`
- Algorithms for generating Prüfer codes, minimum spanning trees, etc.

### Tree
- `AugmentedIntervalTree.java` - Interval tree (augmented BST)
- `RedBlackTree.java` - Red-Black balanced BST with full operations
- `RedBlackTreeMap.java` - Map implementation using Red-Black tree (achieves similar perf to Java TreeMap)
- `UnionFind.java` - Union-Find (Disjoint Set) data structure with union-by-rank and path compression
- `VanEmdeBoasTree.java`- van Emde Boas Tree supporting integers and longs (with sizes 16, 256, 65536, 4294967296)
- `BinomialHeap.java` - Binomial Heap achieving constant time insert time

### Sketch
- `BloomFilter.java` - Bloom Filter for probabilistic membership queries
- `CountMinSketch.java` - Count-min sketch for estimating counts of events
- `HyperLogLog.java` - HyperLogLog sketch for estimating number of distinct events in stream

## Future Additions

Planned additions include:
- SkipList
- Ford-Fulkerson max-flow/min-cut
- AVL Tree
- B-Tree
- Segment Tree
- MinHash (Locality Sensitive Hashing)
- Cuckoo Hash
- 2-SAT linear-time implementation
- Trie
- Stoer–Wagner algorithm
- Kosaraju's Algorithm