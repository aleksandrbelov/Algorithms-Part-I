package tree;

public class BTS<Key extends Comparable<Key>, Value> {

    private Node root;

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    public Value get(Key key){
        return get(root, key);
    }

    public void delete(Key key) {
        delete(root, key);
    }

    private Node delete(Node root, Key key) {
        if (root == null) return null;
        int compare = key.compareTo(root.key);
        if (compare < 0) {
            root.left = delete(root.left, key);
        } else if (compare > 0) {
            root.right = delete(root.right, key);
        } else {
            if (root.right == null) return root.left;
            if (root.left == null) return root.right;
            Node t = root;
            root = min(t.right);
            root.right = deleteMin(t.right);
            root.left = t.left;
        }
        return root;
    }

    private Node deleteMin(Node root) {
        if (root.left == null) return root.right;
        root.left = deleteMin(root.left);
        return root;
    }

    private Node min(Node root) {
        if (root.left == null) return root;
        return min(root.left);
    }

    private Value get(Node root, Key key) {
        if (root == null) return null;
        int compare = key.compareTo(root.key);
        if (compare < 0) return get(root.left, key);
        if (compare > 0) return get(root.right, key);
        return root.value;
    }

    private Node put(Node root, Key key, Value value) {
        if (root == null) return new Node(key, value);
        int compare = key.compareTo(root.key);
        if (compare < 0){
            root.left = put(root.left, key, value);
        } else if (compare > 0) {
            root.right = put(root.right, key, value);
        } else {
            root.value = value;
        }
        return root;
    }

    private class Node {
        private Key key;
        private Value value;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        private Node left;
        private Node right;

    }

    public static void main(String[] args) {
        BTS<String, Integer> bts = new BTS<>();
        bts.put("K", 1);
        bts.put("O", 2);
        bts.put("J", 3);
        bts.get("K");
        bts.get("O");
        bts.get("J");
    }
}
