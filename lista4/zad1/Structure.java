abstract class Structure {

    abstract void insert(String key);

    abstract Node search(String key);

    abstract void find(String key);

    abstract Node min(Node node);

    abstract Node max(Node node);

    abstract Node successor(String key);

    abstract Node remove(String key);

    abstract void inOrder(Node node);

    abstract void print(Node node);

    abstract Node getRoot();

    abstract int getCompNum();
}