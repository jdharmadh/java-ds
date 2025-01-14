package io.github.jdharmadh.ds;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        AugmentedIntervalTree<Integer> tree = new AugmentedIntervalTree<>();
        tree.insert(new Interval<Integer>(2, 6));
        tree.insert(new Interval<Integer>(3, 4));
        tree.insert(new Interval<Integer>(1, 5));
        tree.insert(new Interval<Integer>(8, 10));
        tree.insert(new Interval<Integer>(9, 13));
        tree.insert(new Interval<Integer>(1, 5));
        // tree.print();
        System.out.println(tree.query(9));
    }
}
