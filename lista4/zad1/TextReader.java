import java.io.*;

class TextReader {

    private int numOfInserts = 0;
    private int numOfRemoves = 0;
    private int numOfFinds = 0;
    private int maxValue = 0;

    void read(Structure structure) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String readLine;
            while ((readLine = in.readLine()) != null && readLine.length() != 0) {
                String[] split = readLine.split("\\s+");
                switch (split[0]) {
                    case "//":
                        break;
                    case "insert":
                        split[1] = split[1].replaceAll("[^\\p{Alpha}]", "");
                        structure.insert(split[1]);
                        numOfInserts++;
                        break;
                    case "delete":
                        structure.remove(split[1]);
                        numOfRemoves++;
                        break;
                    case "find":
                        structure.find(split[1]);
                        numOfFinds++;
                        break;
                    case "min":
                        structure.print(structure.min(structure.getRoot()));
                        Helper.newLine();
                        break;
                    case "max":
                        structure.print(structure.max(structure.getRoot()));
                        Helper.newLine();
                        break;
                    case "successor":
                        structure.print(structure.successor(split[1]));
                        Helper.newLine();
                        break;
                    case "inorder":
                        structure.inOrder(structure.getRoot());
                        Helper.newLine();
                        break;
                    case "load":
                        File i = new File(split[1]);
                        BufferedReader u = new BufferedReader(new FileReader(i));
                        String line;
                        while ((line = u.readLine()) != null) {
                            String[] arguments = line.split("\\s+");
                            for (String argument : arguments) {
                                argument = argument.replaceAll("[^\\p{Alpha}]", "");
                                structure.insert(argument);
                                numOfInserts++;
                            }
                        }
                        break;
                        case "findall":
                        File j = new File(split[1]);
                        BufferedReader r = new BufferedReader(new FileReader(j));
                        String all;
                        while ((all = r.readLine()) != null) {
                            String[] arguments = all.split("\\s+");
                            for (String argument : arguments) {
                                argument = argument.replaceAll("[^\\p{Alpha}]", "");
                                structure.find(argument);
                                numOfFinds++;
                            }
                        }
                        break;    
                    default:
                        if (!(Helper.isInteger(split[0])))
                            System.err.println("\nAt least one of arguments is a function.");
                        break;
                }
                if (maxValue < (numOfInserts - numOfRemoves))
                    maxValue = numOfInserts - numOfRemoves;
            }
        } catch (IOException e) {
            System.err.println("Something wrong with file.");
        }
        System.err.println("\nNumber of inserts: " + numOfInserts);
        System.err.println("Number of removes: " + numOfRemoves);
        System.err.println("Number of finds: " + numOfFinds);
        System.err.println("Max number of items: " + maxValue);
        System.err.println("Number of items on the end: " + (numOfInserts - numOfRemoves));
        // System.err.println("Number of compers: " + (structure.getCompNum()));
    }
}