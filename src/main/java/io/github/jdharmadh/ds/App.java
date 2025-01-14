package io.github.jdharmadh.ds;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        AugmentedIntervalTree<Integer> tree = new AugmentedIntervalTree<>();
        tree.insert(new Interval<Integer>(2, 6));
        tree.insert(new Interval<Integer>(3, 4));
        tree.insert(new Interval<Integer>(1, 5));
        tree.print();
    }
}
