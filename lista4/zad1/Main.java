public class Main {
    public static void main(String[] args) {

        TextReader reader = new TextReader();
        long startTime;

        if (args[0].equals("--type")) {
            Structure structure;
            switch (args[1]) {
            case "bst": {
                structure = new BST();
                startTime = System.nanoTime();
                reader.read(structure);
                System.err.println("Time of alghoritm: " + (System.nanoTime() - startTime) / 1000);
            }
                break;
            case "rbt": {
                structure = new RBT();
                startTime = System.nanoTime();
                reader.read(structure);
                System.err.println("Time of alghoritm: " + (System.nanoTime() - startTime) / 1000);
            }
                break;
            case "hmap": {
                structure = new HMAP();
                startTime = System.nanoTime();
                reader.read(structure);
                System.err.println("Time of alghoritm: " + (System.nanoTime() - startTime) / 1000);
            }
                break;
            default:
                System.err.println("Wrong arguments.");
            }
        } else {
            System.err.println("Wrong arguments.");
        }
    }
}