package pq;

public interface PriorityQueue<Key extends Comparable<Key>> {

    void insert(Key v);
    Key delmax();
    boolean isEmpty();

}
