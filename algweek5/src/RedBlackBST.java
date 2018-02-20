public class RedBlackBST<Key extends Comparable<Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    public Node getRoot() {
        return root;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node root, Key key) {
        if (root == null) {
            return null;
        }
        int compare = key.compareTo(root.key);
        if (compare < 0) {
            return get(root.left, key);
        } else if (compare > 0) {
            return get(root.right, key);
        }
        return root.value;
    }

    public void push(Key key, Value value){
        root = push(root, key, value);
    }

    private Node push(Node root, Key key, Value value) {
        if (root == null) return new Node(key, value, RED);

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = push(root.left, key, value);
        } else if (cmp > 0) {
            root.right = push(root.right, key, value);
        } else {
            root.value = value;
        }
        if (isRed(root.right) && !isRed(root.left)) root = rotateLeft(root);
        if (isRed(root.left) && isRed(root.left.left)) root = rotateRight(root);
        if (isRed(root.left) && isRed(root.right)) flipColors(root);
        return root;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private boolean isRed(Node root) {
        return root != null && root.color == RED;
    }

    public class Node {
        private Key key;
        private Value value;

        private Node left;
        private Node right;
        private boolean color;

        public Node(Key key, Value value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }
    }
}

class Test {
    public static void main(String[] args) {
        RedBlackBST<Integer, Integer> redBlackBST = new RedBlackBST<>();
        for (int i = 0; i < 12; i++) {
            redBlackBST.push(i, i);
        }
        RedBlackBST<Integer, Integer>.Node root = redBlackBST.getRoot();


    }
}
