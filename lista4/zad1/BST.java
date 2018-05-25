class BST extends Structure {

    private int numOfComp = 0;
    private Node root = null;

    class BSTNode extends Node {
        BSTNode(String key) {
            this.key = key;
        }

    }

    @Override
    void insert(String key) {
        if (root == null)
            root = new BSTNode(key);
        else {
            Node tmp = root;
            Node parent = null;
            while (tmp != null) {
                parent = tmp;
                tmp = Helper.isMoreThan(tmp.key, key) ? tmp.left : tmp.right;
            }
            if (Helper.isMoreThan(parent.key, key)) {
                parent.left = new BSTNode(key);
                parent.left.parent = parent;
            } else {
                parent.right = new BSTNode(key);
                parent.right.parent = parent;
            }
        }
    }

    @Override
    Node search(String key) {
        Node tmp = root;
        while (tmp != null && !tmp.key.equals(key))
            tmp = Helper.isMoreThan(tmp.key, key) ? tmp.left : tmp.right;
        if (tmp == null)
            return null;
        return tmp;
    }

    @Override
    void find(String key) {
        Node tmp = root;
        while (tmp != null && !tmp.key.equals(key)) {
            tmp = Helper.isMoreThan(tmp.key, key) ? tmp.left : tmp.right;
            numOfComp++;
        }
        if (tmp == null) {
            System.out.println("0");
            return;
        }
        System.out.println("1");
    }

    @Override
    Node min(Node node) {
        if (root == null)
            return null;
        while (node.left != null)
            node = node.left;
        return node;
    }

    @Override
    Node max(Node node) {
        if (root == null)
            return null;
        while (node.right != null)
            node = node.right;
        return node;
    }

    @Override
    Node successor(String key) {
        Node node = this.search(key);
        if (node == null)
            return null;
        if (node.right != null) {
            node = node.right;
            while (node.left != null)
                node = node.left;
            return node;
        } else if (node != root && node != this.max(root)) {
            Node parent = node.parent;
            while (parent != root && Helper.isMoreThan(node.key, parent.key))
                parent = parent.parent;
            return parent;
        } else
            return null; // no successor
    }

    @Override
    Node remove(String key) {
        Node node = this.search(key);
        if (node == null) {
            // System.out.println("Nothing to remove.");
            return null;
        }
        Node parent = node.parent;
        Node tmp;
            if (node.left != null && node.right != null) {
                tmp = this.remove(this.successor(key).key);
                tmp.left = node.left;
                if (tmp.left != null)
                    tmp.left.parent = tmp;
                tmp.right = node.right;
                if (tmp.right != null)
                    tmp.right.parent = tmp;
            } else
                tmp = (node.left != null) ? node.left : node.right;
            if (tmp != null)
                tmp.parent = parent;
            if (parent == null)
                root = tmp;
            else if (parent.left == node)
                parent.left = tmp;
            else
                parent.right = tmp;
        return node;
    }

    @Override
    void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
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