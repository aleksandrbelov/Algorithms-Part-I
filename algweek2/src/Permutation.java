import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> strings = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            strings.enqueue(StdIn.readString());
        }
        while (strings.size() > k) {
            strings.dequeue();
        }
        for (String s: strings) {
            System.out.println(s);
        }
    }
}
