package pq;

public class MaxPQ<T extends Comparable<T>> implements PriorityQueue<T> {

    private T[] arr;
    private int n;

    public MaxPQ(int max) {
        arr = (T[]) new Comparable[max];
    }

    public MaxPQ(T[] arr) {
        this.arr = arr;
        n = arr.length;
    }

    @Override
    public void insert(T v) {
        arr[n++] = v;
    }

    @Override
    public T delmax() {
        int max = n - 1;
        for (int i = 0; i < n - 1; i++) {
            if(less(max, i)){
                max = i;
            }
        }
        exch(max, n - 1);
        return arr[--n];
    }

    private void exch(int i, int j) {
        T swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }

    private boolean less(int i, int j) {
        return arr[i].compareTo(arr[j]) < 0;
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }
}
