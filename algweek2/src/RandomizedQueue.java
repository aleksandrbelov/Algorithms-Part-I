import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Object[] arr;
    private int size;

    public RandomizedQueue() {
        arr = new Object[2];
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(17);
        rq.dequeue();     //==> 17
        rq.size();        //==> 1
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // remove and return a random item

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == arr.length) {
            resize(arr.length * 2);
        }
        arr[size++] = item;
    }
    // return a random item (but do not remove it)

    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException();
        int uniform = StdRandom.uniform(size); //[0, size)
        Item item = (Item) arr[uniform];
        if (size-- == 1) {
            arr[uniform] = null;
            return item;
        }
        if (uniform != size) {
            arr[uniform] = arr[size];
        }
        arr[size] = null;

        if (size > 0 && arr.length / 4 == size) {
            resize(arr.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (size == 0) throw new NoSuchElementException();
        return (Item) arr[StdRandom.uniform(size)];
    }

    private void resize(int newLength) {
        arr = Arrays.copyOf(arr, newLength);
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        private Object[] randomArr;
        private int current;

        RandomIterator() {
            randomArr = Arrays.copyOf(arr, size);
            StdRandom.shuffle(randomArr);
        }

        @Override
        public boolean hasNext() {
            return current < randomArr.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return (Item) randomArr[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
