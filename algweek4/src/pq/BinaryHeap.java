package pq;

public class BinaryHeap<T extends Comparable<T>>{

    private T[] pq;
    private int n = 0;

    public BinaryHeap(int size){
        pq = (T[]) new Comparable[size+1];
    }

    public void insert(T v){
        pq[++n] = v;
        swim(n);
    }

    public T delMax(){
        T item = pq[1];
        exch(1, n--);
        pq[n+1] = null;
        sink(1);
        return item;
    }

    private void swim(int k){
        while (less(k/2, k) && k > 1) {
            exch(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k){
        while (2*k <= n){
            int j = 2*k;
            if (j < n && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j){
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j){
        T temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    public static void sort(Comparable[] a){
        int n = a.length;
        for (int k = n/2; k >= 1; k--) {
            sink(a, k, n);
        }
        while (n > 1) {
            exch(a, 1, n--);
            sink(a, 1, n);
        }
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = temp;
    }

    private static void sink(Comparable[] a, int k, int n) {
        while (2*k <= n){
            int j = 2*k;
            if (j < n && less(a, j, j+1)) j++;
            if (!less(a, k, j)) break;
            exch(a, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] a, int i, int j) {
        return a[i-1].compareTo(a[j-1]) < 0;
    }

    public static void main(String[] args) {
        Integer[] arr = {7, 1, 3, 2, 5};
        sort(arr);
        for (Integer i :
                arr) {
            System.out.println(i);
        }
    }
}
