import java.util.concurrent.ExecutionException;

class HMAP extends Structure {

    private int numOfComp = 0;
    private final static int TABLE_SIZE = 128;
    private final static int MAX_SIZE = 300;
    private Node table[] = new HMAPNode[TABLE_SIZE];
    private int sizes[] = new int[TABLE_SIZE];

    class HMAPNode extends Node {
        HMAPNode(String key) {
            this.key = key;
            this.structure = new List();
        }
    }

    @Override
    void insert(String key) {
        int hash = getHash(key);
        if (table[hash] == null) {
            table[hash] = new HMAPNode(key);
        }
        table[hash].structure.insert(key);
        sizes[hash] += 1;
        if (sizes[hash] == MAX_SIZE) {
            Structure tree = new RBT();
            Node prescribe = table[hash].structure.getRoot();
            while (prescribe != null) {
                tree.insert(prescribe.key);
                prescribe = prescribe.right;
            }
            table[hash].structure = tree;
        }
    }

    @Override
    Node search(String key) {
        int hash = getHash(key);
        return table[hash].structure.search(key);
    }

    @Override
    void find(String key) {
        int hash = getHash(key);
        if (table[hash] != null) {
            table[hash].structure.find(key);
        } else
            System.out.println("0");
    }

    @Override
    Node min(Node node) {
        return null;
    }

    @Override
    Node max(Node node) {
        return null;
    }

    @Override
    Node successor(String key) {
        return null;
    }

    @Override
    Node remove(String key) {
        int hash = getHash(key);
        if (table[hash] != null)
            if (table[hash].structure.remove(key) != null) {
                sizes[hash] -= 1;
            }
        if (sizes[hash] == MAX_SIZE) {
            Node prescribe = table[hash].structure.getRoot();
            Structure list = new List();
            prescribe(prescribe, list);
            table[hash].structure = list;
        }
        return null;
    }

    @Override
    void inOrder(Node node) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (table[i] != null)
                table[i].structure.inOrder(table[i].structure.getRoot());
            Helper.newLine();
        }
        // Helper.newLine();
    }

    @Override
    void print(Node node) {
        try {
            System.out.print(node.key);
        } catch (NullPointerException ex) {
            // System.out.print("Nothing here.");
            System.out.print("");
        }
    }

    @Override
    Node getRoot() {
        return null;
    }

    @Override
    int getCompNum() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            try {
                numOfComp += table[i].structure.getCompNum();
            } catch (Exception ignore) {
            }
        }
        return numOfComp;
    }

    private int getHash(String key) {
        return Math.abs(key.hashCode() % TABLE_SIZE);
    }

    private void prescribe(Node node, Structure list) {
        if (node != null) {
            if (!node.key.equals(""))
                list.insert(node.key);
            prescribe(node.right, list);
            prescribe(node.left, list);
        }
    }
}
