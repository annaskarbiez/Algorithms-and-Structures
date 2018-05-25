class RBT extends Structure {

    private int numOfComp = 0;    
    private final Node NiL = new RBTNode("");
    private final int RED = 0;
    private final int BLACK = 1;
    private Node root = NiL;

    class RBTNode extends Node {

        RBTNode(String key) {
            this.color = BLACK;
            this.key = key;
        }
    }

    @Override
    void insert(String key) {
        Node node = new RBTNode(key);
        node.left = NiL;
        node.right = NiL;
        node.parent = NiL;
        Node temp = root;
        if (root == NiL) {
            root = node;
            node.color = BLACK;
        } else {
            node.color = RED;
            while (true) {
                if (Helper.isMoreThan(temp.key, node.key)) {
                    if (temp.left == NiL) {
                        temp.left = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else {
                    if (temp.right == NiL) {
                        temp.right = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            fixTree(node);
        }
    }

    @Override
    Node search(String key) {
        Node tmp = root;
        while (tmp != null && !tmp.key.equals(key))
            tmp = Helper.isMoreThan(tmp.key, key) ? tmp.left : tmp.right;
        if (tmp == null) {
            return null;
        }
        return tmp;
    }

    @Override
    void find(String key) {
        Node tmp = root;
        while (tmp != NiL && !tmp.key.equals(key)) {
            tmp = Helper.isMoreThan(tmp.key, key) ? tmp.left : tmp.right;
            numOfComp++;
        }
        if (tmp == NiL) {
            System.out.println("0");
            return;
        }
        System.out.println("1");
    }

    @Override
    Node min(Node node) {
        if (node == null)
            return null;
        while (node.left != null && node.left != NiL) {
            node = node.left;
        }
        return node;
    }

    @Override
    Node max(Node node) {
        if (node == null)
            return null;
        while (node.right != null && node.right != NiL) {
            node = node.right;
        }
        return node;
    }

    @Override
    Node successor(String key) {
        Node node = this.search(key);
        if (node == null)
            return null;
        if (node.right != NiL) {
            node = node.right;
            while (node.left != NiL)
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
        Node node;
        if ((node = search(key)) == null) {
            // System.out.println("Nothing to remove.");
            return null;
        }
        Node x;
        Node y = node;
        int original_color = y.color;

        if (node.left == NiL) {
            x = node.right;
            swap(node, node.right);
        } else if (node.right == NiL) {
            x = node.left;
            swap(node, node.left);
        } else {
            y = min(node.right);
            original_color = y.color;
            x = y.right;
            if (y.parent == node)
                x.parent = y;
            else {
                swap(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }
            swap(node, y);
            y.left = node.left;
            y.left.parent = y;
            y.color = node.color;
        }
        if (original_color == BLACK)
            deleteFix(x);
        return node;
    }

    @Override
    void inOrder(Node node) {
        if (node != NiL) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    @Override
    void print(Node node) {
        try {
            if (node != NiL)
                System.out.print(node.key);
            else
                // System.out.print("Nothing here.");
                System.out.print("");
        } catch (NullPointerException ex) {
            // System.out.print("Nothing here.");
            System.out.print("");
        }
    }

    @Override
    Node getRoot() {
        return this.root;
    }

    private void fixTree(Node node) {
        while (node.parent.color == RED) {
            Node uncle;
            if (node.parent == node.parent.parent.left) {
                uncle = node.parent.parent.right;

                if (uncle != NiL && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.right) {
                    node = node.parent;
                    rotateLeft(node);
                }
                node.parent.color = BLACK;
                node.parent.parent.color = RED;
                rotateRight(node.parent.parent);
            } else {
                uncle = node.parent.parent.left;
                if (uncle != NiL && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.left) {
                    node = node.parent;
                    rotateRight(node);
                }
                node.parent.color = BLACK;
                rotateLeft(node.parent.parent);
            }
        }
        root.color = BLACK;
    }

    private void rotateLeft(Node node) {
        if (node.parent != NiL) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != NiL) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {
            Node right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = NiL;
            root = right;
        }
    }

    private void rotateRight(Node node) {
        if (node.parent != NiL) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != NiL) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {
            Node left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = NiL;
            root = left;
        }
    }

    private void deleteFix(Node x) {
        try {
            while (x != root && x.color == BLACK) {
                if (x == x.parent.left) {
                    Node brother = x.parent.right;
                    if (brother.color == RED) {
                        brother.color = BLACK;
                        x.parent.color = RED;
                        rotateLeft(x.parent);
                        brother = x.parent.right;
                    }
                    if (brother.left.color == BLACK && brother.right.color == BLACK) {
                        brother.color = RED;
                        x = x.parent;
                        continue;
                    } else if (brother.right.color == BLACK) {
                        brother.left.color = BLACK;
                        brother.color = RED;
                        rotateRight(brother);
                        brother = x.parent.right;
                    }
                    if (brother.right.color == RED) {
                        brother.color = x.parent.color;
                        x.parent.color = BLACK;
                        brother.right.color = BLACK;
                        rotateLeft(x.parent);
                        x = root;
                    }
                } else {
                    Node brother = x.parent.left;
                    if (brother.color == RED) {
                        brother.color = BLACK;
                        x.parent.color = RED;
                        rotateRight(x.parent);
                        brother = x.parent.left;
                    }
                    if (brother.right.color == BLACK && brother.left.color == BLACK) {
                        brother.color = RED;
                        x = x.parent;
                        continue;
                    } else if (brother.left.color == BLACK) {
                        brother.right.color = BLACK;
                        brother.color = RED;
                        rotateLeft(brother);
                        brother = x.parent.left;
                    }
                    if (brother.left.color == RED) {
                        brother.color = x.parent.color;
                        x.parent.color = BLACK;
                        brother.left.color = BLACK;
                        rotateRight(x.parent);
                        x = root;
                    }
                }
            }
            x.color = BLACK;
        } catch (Exception ignore) {
        }
    }

    private void swap(Node node, Node target) {
        if (node.parent == NiL) {
            root = target;
        } else if (node == node.parent.left) {
            node.parent.left = target;
        } else
            node.parent.right = target;
        target.parent = node.parent;
    }

    @Override
    int getCompNum() {
        return numOfComp;
    }
}
