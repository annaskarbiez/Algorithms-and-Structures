import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int length;
        boolean forward = true;

        try {
            if (!(args[0].equals("--type") && args[2].equals("--comp"))) {
                System.err.println("Wrong arguments.");
                System.exit(0);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.err.println("Too less arguments.");
            System.exit(0);
        }

        try {
            switch (args[3]) {
                case "<=":
                forward = true;

                break;
                case ">=":
                forward = false;
                break;
                default:
                System.exit(0);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.err.println("Wrong arguments.");
            System.exit(0);
        }

        Scanner input = new Scanner(System.in);
        length = input.nextInt();
        int[] array = new int[length];
        for (int i = 0; i < length; i++)
            array[i] = input.nextInt();

        switch (args[1]) {
            case "insert": {
                long startTime = System.nanoTime();
                InsertionSort sort = new InsertionSort(array, length, forward);
                long estimatedTime = System.nanoTime() - startTime;
                System.err.println("\nCompare: " + sort.counterC);
                System.err.println("Swap: " + sort.counterS + "\n");
                print(array);
                System.out.println("Time: " + estimatedTime);
            }
            break;
            case "merge": {
                long startTime = System.nanoTime();
                MergeSort sort = new MergeSort(array, length, forward);
                long estimatedTime = System.nanoTime() - startTime;
                System.err.println("\nCompare: " + sort.counterC);
                System.err.println("Swap: " + sort.counterS + "\n");
                print(array);
                System.out.println("Time: " + estimatedTime);
            }
            break;
            case "quick": {
                long startTime = System.nanoTime();
                QuickSort sort = new QuickSort(array, length, forward);
                long estimatedTime = System.nanoTime() - startTime;
                System.err.println("\nCompare: " + sort.counterC);
                System.err.println("Swap: " + sort.counterS + "\n");
                print(array);
                System.out.println("Time: " + estimatedTime);
            }
            break;
            case "dualquick": {
                long startTime = System.nanoTime();
                DualQuickSort sort = new DualQuickSort(array, length, forward);
                long estimatedTime = System.nanoTime() - startTime;
                System.err.println("\nCompare: " + sort.counterC);
                System.err.println("Swap: " + sort.counterS + "\n");
                print(array);
                System.out.println("Time: " + estimatedTime);
            }
            break;
            default:
            System.exit(0);
        }

    }

    private static void print(int array[]) {
        for (int anArray : array) System.out.print(anArray + " ");
    }

}

class InsertionSort {
    int counterC = 0;
    int counterS = 0;

    InsertionSort(int[] array, int length, boolean forward) {
        insertionSort(array, length, forward);
    }

    private void insertionSort(int[] array, int length, boolean forward) {
        if (forward) {
            for (int index = 1; index < length; index++) {
                int value = array[index];
                int i = index - 1;
                while (i >= 0) {
                    System.err.println("compare: while (value <= array[i])");
                    counterC++;
                    if (value <= array[i]) {
                        array[i + 1] = array[i];
                        array[i] = value;
                        System.err.println("swap: array[i + 1] = array[i]");
                        counterS++;
                    }
                    i--;
                }
            }
        } else {
            for (int index = 1; index < length; index++) {
                int value = array[index];
                int i = index - 1;
                while (i >= 0) {
                    System.err.println("compare: while (value >= array[i])");
                    counterC++;
                    if (value >= array[i]) {
                        array[i + 1] = array[i];
                        array[i] = value;
                        System.err.println("swap: array[i + 1] = array[i]");
                        counterS++;
                    }
                    i--;
                }
            }
        }
    }
}

class MergeSort {
    int counterC = 0;
    int counterS = 0;

    MergeSort(int[] array, int length, boolean forward) {
        mergeSort(array, length, forward);
    }

    private void mergeSort(int[] array, int length, boolean forward) {

        if (forward) {
            if (length > 1) {
                int mid = length / 2;

                int[] left = Arrays.copyOfRange(array, 0, array.length / 2);
                int[] right = Arrays.copyOfRange(array, mid, array.length);

                mergeSort(left, mid, true);
                mergeSort(right, (length - mid), true);

                int i = 0, j = 0, k = 0;
                while (i < mid && j < (length - mid)) {
                    if (left[i] < right[j]) {
                        System.err.println("compare: if (left[i] < right[j])");
                        counterC++;
                        array[k] = left[i];
                        i++;
                    } else {
                        System.err.println("compare: if (left[i] < right[j])");
                        counterC++;
                        System.err.println("pseudoswap");
                        counterS++;
                        array[k] = right[j];
                        j++;
                    }
                    k++;
                }
                while (i < mid) {
                    array[k] = left[i];
                    i++;
                    k++;
                }
                while (j < (length - mid)) {
                    array[k] = right[j];
                    j++;
                    k++;
                }
            }
        } else {
            if (length > 1) {
                int mid = length / 2;

                int[] left = Arrays.copyOfRange(array, 0, array.length / 2);
                int[] right = Arrays.copyOfRange(array, mid, array.length);

                mergeSort(left, mid, false);
                mergeSort(right, (length - mid), false);

                int i = 0, j = 0, k = 0;
                while (i < mid && j < (length - mid)) {
                    if (left[i] > right[j]) {
                        System.err.println("compare: if (left[i] > right[j])");
                        counterC++;

                        array[k] = left[i];
                        i++;
                    } else {
                        System.err.println("compare: if (left[i] < right[j])");
                        counterC++;
                        System.err.println("pseudoswap");
                        counterS++;
                        array[k] = right[j];
                        j++;
                    }
                    k++;
                }
                while (i < mid) {
                    array[k] = left[i];
                    i++;
                    k++;
                }
                while (j < (length - mid)) {
                    array[k] = right[j];
                    j++;
                    k++;
                }
            }
        }
    }
}

class QuickSort {
    int counterC = 0;
    int counterS = 0;

    QuickSort(int array[], int length, boolean forward) {
        quickSort(array, 0, length - 1, forward);
    }

    private void quickSort(int array[], int begin, int length, boolean forward) {

        if (forward) {
            int i = begin, j = length;
            int pivot = array[begin + (length - begin) / 2];

            while (i <= j) {
                while (array[i] < pivot) {
                    System.err.println("compare: while (array[i] < pivot)");
                    counterC++;
                    i++;
                }
                while (array[j] > pivot) {
                    System.err.println("compare: while (array[j] > pivot)");
                    counterC++;
                    j--;
                }
                if (i <= j) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    System.err.println("swap: array[i] = array[j];");
                    counterS++;
                    i++;
                    j--;
                }
            }
            if (begin < j) {
                quickSort(array, begin, j, true);
            }
            if (i < length) {
                quickSort(array, i, length, true);
            }
        } else {
            int i = begin, j = length;
            int pivot = array[begin + (length - begin) / 2];

            while (i <= j) {
                System.err.println("compare: while (array[i] > pivot)");
                counterC++;
                while (array[i] > pivot) {
                    i++;
                }
                System.err.println("compare: while (array[j] < pivot)");
                counterC++;
                while (array[j] < pivot) {
                    j--;
                }
                if (i <= j) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    System.err.println("swap: array[i] = array[j];");
                    counterS++;
                    i++;
                    j--;
                }
            }
            if (begin < j) {
                quickSort(array, begin, j, false);
            }
            if (i < length) {
                quickSort(array, i, length, false);
            }
        }
    }
}

class DualQuickSort {
    int counterC = 0;
    int counterS = 0;

    DualQuickSort(int array[], int length, boolean forward) {
        dualQuickSort(array, 0, length - 1, forward);
    }

    private void dualQuickSort(int array[], int begin, int length, boolean forward) {
        if (forward) {
            if (length <= begin)
                return;
            int k = begin + 1;
            int h = k;
            int l = length - 1;
            int temp;

            System.err.println("compare: array[begin] > array[length]");
            counterC++;
            if (array[begin] > array[length]) {
                temp = array[begin];
                array[begin] = array[length];
                array[length] = temp;
                System.err.println("swap: array[begin] = array[length]");
                counterS++;
            }
            while (k <= l) {
                if (array[k] < array[begin]) {
                    System.err.println("compare: array[k] < array[begin])");
                    counterC++;
                    temp = array[h];
                    array[h] = array[k];
                    array[k] = temp;
                    System.err.println("swap: array[h] = array[k]");
                    counterS++;
                    h++;
                    k++;
                } else if (array[k] > array[length]) {
                    System.err.println("compare: array[k] > array[length]");
                    counterC+=2;
                    temp = array[k];
                    array[k] = array[l];
                    array[l] = temp;
                    l--;
                } else {
                    System.err.println("compare: else");
                    counterC+=2;
                    k++;
                }
            }
            h--;
            l++;

            temp = array[begin];
            array[begin] = array[h];
            array[h] = temp;
            System.err.println("swap: array[begin] = array[h]");
            counterS++;

            temp = array[length];
            array[length] = array[l];
            array[l] = temp;
            System.err.println("swap: array[length] = array[l]");
            counterS++;

            dualQuickSort(array, begin, h - 1, true);
            dualQuickSort(array, h + 1, l - 1, true);
            dualQuickSort(array, l + 1, length, true);
        } else {
            if (length <= begin)
                return;
            int k = begin + 1;
            int h = k;
            int l = length - 1;
            int temp;

            System.err.println("compare: array[begin] < array[length]");
            counterC++;
            if (array[begin] < array[length]) {
                temp = array[begin];
                array[begin] = array[length];
                array[length] = temp;
                System.err.println("swap: array[begin] = array[length]");
                counterS++;
            }
            while (k <= l) {
                if (array[k] > array[begin]) {
                    System.err.println("compare: array[k] > array[begin]");
                    counterC++;
                    temp = array[h];
                    array[h] = array[k];
                    array[k] = temp;
                    System.err.println("swap: array[h] = array[k]");
                    counterS++;
                    h++;
                    k++;
                } else if (array[k] < array[length]) {
                    System.err.println("compare: array[k] < array[length]");
                    counterC+=2;
                    temp = array[k];
                    array[k] = array[l];
                    array[l] = temp;
                    System.err.println("swap: array[k] = array[l]");
                    counterS++;
                    l--;
                } else {
                    System.err.println("compare: else");
                    counterC+=2;
                    k++;
                }
            }
            h--;
            l++;

            temp = array[begin];
            array[begin] = array[h];
            array[h] = temp;
            System.err.println("swap: array[begin] = array[h]");
            counterS++;

            temp = array[length];
            array[length] = array[l];
            array[l] = temp;
            System.err.println("swap: array[length] = array[l]");
            counterS++;

            dualQuickSort(array, begin, h - 1, false);
            dualQuickSort(array, h + 1, l - 1, false);
            dualQuickSort(array, l + 1, length, false);
        }
    }
}
