class Helper {
    static boolean isMoreThan(String a, String b) {
        return a.compareTo(b) >= 0;
    }

    static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static void newLine() {
        System.out.println();
    }
}