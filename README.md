# Data Structures!

Some Java implementations of fundamental data structures and algorithms. This repository serves as both a reference and a learning resource for concepts I'm interested in.

### Objects
- `Interval.java` - Implementation for interval representation and operations

### Graph
- `DirectedGraph.java`
- `UndirectedGraph.java` 
- `UndirectedWeightedGraph.java`
- `WeightedDirectedGraph.java`

### Tree
- `AugmentedIntervalTree.java` - Interval tree (augmented BST)
- `RedBlackTree.java` - Red-Black balanced BST with full operations
- `RedBlackTreeMap.java` - Map implementation using Red-Black tree (achieves similar perf to Java TreeMap)
- `UnionFind.java` - Union-Find (Disjoint Set) data structure with union-by-rank and path compression
- `VanEmdeBoasTree.java`- van Emde Boas Tree supporting integers and longs (with sizes 16, 256, 65536, 4294967296)

### Sketch
- `BloomFilter.java` - Bloom Filter for probabilistic membership queries
- `CountMinSketch.java` - Count-min sketch for estimating counts of events

## Future Additions

Planned additions include:
- SkipList
- Ford-Fulkerson max-flow/min-cut
- AVL Tree
- B-Tree
- Binomial Heap
- Segment Tree
- CountDistinct Sketch (HyperLogLog)
- MinHash (Locality Sensitive Hashing)
- Cuckoo Hash
- 2-SAT linear-time implementation
- Trie
- Stoer–Wagner algorithm