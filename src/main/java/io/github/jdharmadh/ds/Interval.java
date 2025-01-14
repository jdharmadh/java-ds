package io.github.jdharmadh.ds;

public class Interval<T extends Comparable<T>> {
    public T start;
    public T end;

    public Interval(T start, T end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end cannot be null");
        }
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("Start cannot be greater than end");
        }
        this.start = start;
        this.end = end;
    }

    public boolean intersects(Interval<T> other) {
        return (this.start.compareTo(other.start) <= 0 && this.end.compareTo(other.start) >= 0) ||
                (other.start.compareTo(this.start) <= 0 && other.end.compareTo(this.start) >= 0);
    }
}