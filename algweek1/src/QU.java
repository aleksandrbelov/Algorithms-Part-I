import java.util.Arrays;

public class QU implements UFI {

    private int[] id;
    private int count;
    private int[] sz;

    public QU(int size){
        this.count = size;
        this.id = new int[size];
        this.sz = new int[size];
        for (int i = 0; i < size; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    @Override
    public int find(int p) {
        if (id[p] == p)
            return p;
        int pid = find(id[p]);
        id[p] = pid;
        return pid;
    }

    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) return;

        if (sz[pRoot] > sz[qRoot]) {
            id[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        } else {
            id[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        }

        count--;

    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int count() {
        return this.count;
    }

    @Override
    public String toString() {
        return "QU{" +
                "id=" + Arrays.toString(id) +
                ", count=" + count +
                '}';
    }
}
