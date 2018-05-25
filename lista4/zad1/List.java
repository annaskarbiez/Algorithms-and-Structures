class List extends Structure {

    private int numOfComp = 0;
    private Node root = null;

    class ListNode extends Node {
        ListNode(String key) {
            this.key = key;
            this.right = null;
        }

    }

    @Override
    void insert(String key) {
        if (root == null) {
            root = new ListNode(key);
        } else {
            Node tmp = root;
            Node left;
            while (tmp != null) {
                if (tmp.right == null) {
                    tmp.right = new ListNode(key);
                    break;
                }
                if (Helper.isMoreThan(key, root.key)) {
                    Node insert = new ListNode(key);
                    insert.right = root;
                    root = insert;
                    break;                    
                }
                left = tmp;
                tmp = tmp.right;
                if (Helper.isMoreThan(key, tmp.key)) {
                    Node insert = new ListNode(key);
                    left.right = insert;
                    insert.right = tmp;
                    break;
                }
            }
        }
    }

    @Override
    Node search(String key) {
        Node tmp = root;
        while (tmp != null && !tmp.key.equals(key))
            tmp = tmp.right;
        if (tmp == null)
            return null;
        return tmp;
    }

    @Override
    void find(String key) {
        Node tmp = root;
        while (tmp != null && !tmp.key.equals(key))
            tmp = tmp.right;
        if (tmp == null) {
            System.out.println("0");
            return;
        }
        System.out.println("1");
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
        Node node = root;
        if (node == null) {
            // System.out.println("Nothing to remove.");
            return null;
        } else if (node.key.equals(key)) {
            if (node.right != null)
                root = root.right;
            else
                root = null;
        } else {
            while (node.right != null)
                if (node.right.key.equals(key)) {
                    node.right = node.right.right;
                    return null;
                } else
                    node = node.right;
        }
        // System.out.println("Nothing to remove.");
        return null;
    }

    @Override
    void inOrder(Node node) {
        while (node != null) {
            System.out.print(node.key + " ");
            node = node.right;
        }
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
        return this.root;
    }

    @Override
    int getCompNum() {
        return numOfComp;
    }
}