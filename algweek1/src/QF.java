import java.util.Arrays;

public class QF implements UFI {

    private int[] id;
    private int count;

    public QF(int size){
        this.count = size;
        this.id = new int[size];
        for (int i = 0; i < size; i++) {
            id[i] = i;
        }
    }

    @Override
    public int find(int p) {
        return id[p];
    }

    @Override
    public void union(int p, int q) {
        int pid = find(p);
        int qid = find(q);

        if (pid == qid) return;

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid){
                id[i] = qid;
            }
        }
        this.count -= 1;
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
        return "QF{" +
                "id=" + Arrays.toString(id) +
                '}';
    }
}
