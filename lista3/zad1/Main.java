import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String Name = "";
        int k = 0;
        Random rand = new Random();

        if (args.length == 0) {
            System.out.println("No parameters");
            System.exit(0);
        }

        if (args[0].equals("--stat")) {
            Name = args[1];
            k = Integer.parseInt(args[2]);
        } else {
            System.err.println("Wrong parameters.");
            System.exit(0);
        }
        PrintWriter saveFile = new PrintWriter(Name + ".txt");

        StringBuilder Num = new StringBuilder("No = \"");
        StringBuilder qsCompare = new StringBuilder("qC = \"");
        StringBuilder qsSwap = new StringBuilder("qS = \"");
        StringBuilder qsTime = new StringBuilder("qT = \"");
        StringBuilder qsMemory = new StringBuilder("qM = \"");
        StringBuilder isCompare = new StringBuilder("iC = \"");
        StringBuilder isSwap = new StringBuilder("iS = \"");
        StringBuilder isTime = new StringBuilder("iT = \"");
        StringBuilder isMemory = new StringBuilder("iM = \"");
        StringBuilder msCompare = new StringBuilder("mC = \"");
        StringBuilder msSwap = new StringBuilder("mS = \"");
        StringBuilder msTime = new StringBuilder("mT = \"");
        StringBuilder msMemory = new StringBuilder("mM = \"");
        StringBuilder rsCompare = new StringBuilder("rC = \"");
        StringBuilder rsSwap = new StringBuilder("rS = \"");
        StringBuilder rsTime = new StringBuilder("rT = \"");
        StringBuilder rsMemory = new StringBuilder("rM = \"");

        for (int n = 100; n <= 10000; n = n + 100) {
            for (int i = 0; i < k; i++) {
                int[] array = new int[n];

                for (int j = 0; j < n; j++) {
                    array[j] = rand.nextInt(n);
                }
                int[] array1 = Arrays.copyOf(array, array.length);
                int[] array2 = Arrays.copyOf(array, array.length);
                int[] array3 = Arrays.copyOf(array, array.length);
                int[] array4 = Arrays.copyOf(array, array.length);
                Num.append(Integer.toString(n)).append(" ");

                long startTimeI = System.nanoTime();
                long startMemoryI = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory());
                InsertionSort is = new InsertionSort(array1, array1.length);
                isMemory.append((Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory()) - startMemoryI).append(" ");
                isTime.append(System.nanoTime() - startTimeI).append(" ");
                isCompare.append(Integer.toString(is.counterC)).append(" ");
                isSwap.append(Integer.toString(is.counterS)).append(" ");

                long startTimeM = System.nanoTime();
                long startMemoryM = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory());
                MergeSort ms = new MergeSort(array2, array2.length);
                msMemory.append((Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory()) - startMemoryM).append(" ");
                msTime.append(System.nanoTime() - startTimeM).append(" ");
                msCompare.append(Integer.toString(ms.counterC)).append(" ");
                msSwap.append(Integer.toString(ms.counterS)).append(" ");

                long startTimeQ = System.nanoTime();
                long startMemoryQ = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory());
                QuickSort qs = new QuickSort(array3, array3.length);
                qsMemory.append((Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory()) - startMemoryQ).append(" ");
                qsTime.append(System.nanoTime() - startTimeQ).append(" ");
                qsCompare.append(Integer.toString(qs.counterC)).append(" ");
                qsSwap.append(Integer.toString(qs.counterS)).append(" ");

                long startTimeR = System.nanoTime();
                long startMemoryR = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory());
                RadixSort rs = new RadixSort(array4, array4.length);
                rsMemory.append((Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory()) - startMemoryR).append(" ");
                rsTime.append(System.nanoTime() - startTimeR).append(" ");
                rsCompare.append(Integer.toString(rs.counterC)).append(" ");
                rsSwap.append(Integer.toString(rs.counterS)).append(" ");
            }
        }
        saveFile.write(Num + "\"\n" + qsCompare + "\"\n" + qsSwap + "\"\n" + qsTime + "\"\n" + qsMemory + "\"\n"
                + msCompare + "\"\n" + msSwap + "\"\n" + msTime + "\"\n" + msMemory + "\"\n" + isCompare + "\"\n"
                + isSwap + "\"\n" + isTime + "\"\n" + isMemory + "\"\n"  + rsCompare + "\"\n" + rsSwap + "\"\n"
                + rsTime + "\"\n" + rsMemory + "\"\n");
        saveFile.close();
    }
}


class InsertionSort {
    int counterC = 0;
    int counterS = 0;

    InsertionSort(int[] array, int length) {
        insertionSort(array, length);
    }

    private void insertionSort(int[] array, int length) {
        for (int index = 1; index < length; index++) {
            int value = array[index];
            int i = index - 1;
            while (i >= 0 && value <= array[i]) {
//                System.err.println("compare: while (value <= array[i])");
                counterC++;
                array[i + 1] = array[i];
//                System.err.println("swap: array[i + 1] = array[i]");
                counterS++;
                array[i] = value;
                i--;
            }
        }
    }
}

class MergeSort {
    int counterC = 0;
    int counterS = 0;

    MergeSort(int[] array, int length) {
        mergeSort(array, length);
    }

    private void mergeSort(int[] array, int length) {

        if (length > 1) {
            int mid = length / 2;

            int[] left = Arrays.copyOfRange(array, 0, array.length / 2);
            int[] right = Arrays.copyOfRange(array, mid, array.length);

            mergeSort(left, mid);
            mergeSort(right, (length - mid));

            int i = 0, j = 0, k = 0;
            while (i < mid && j < (length - mid)) {
                if (left[i] < right[j]) {
//                    System.err.println("compare: if (left[i] < right[j])");
                    counterC++;
                    array[k] = left[i];
//                    System.err.println("pseudoswap");
                    counterS++;
                    i++;
                } else {
//                    System.err.println("pseudoswap");
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

class QuickSort {
    int counterC = 0;
    int counterS = 0;

    QuickSort(int array[], int length) {
        quickSort(array, 0, length - 1);
    }

    private void quickSort(int array[], int begin, int length) {

        int i = begin, j = length;
        int pivot = array[begin + (length - begin) / 2];

        while (i <= j) {
            while (array[i] < pivot) {
                //               System.err.println("compare: while (array[i] < pivot)");
                counterC++;
                i++;
            }
            while (array[j] > pivot) {
//                System.err.println("compare: while (array[j] > pivot)");
                counterC++;
                j--;
            }
            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
//                System.err.println("swap: array[i] = array[j];");
                counterS++;
                i++;
                j--;
            }
        }
        if (begin < j) {
            quickSort(array, begin, j);
        }
        if (i < length) {
            quickSort(array, i, length);
        }

    }
}

class RadixSort {
    int counterC = 0;
    int counterS = 0;

    RadixSort(int array[], int length) {
        int max = getMax(array, length);
        for (int i = 1; max / i > 0; i *= Math.floor(Math.log(max))) {
            countingSort(array, length, i);
        }
    }

    private int getMax(int[] array, int n) {
        int max = array[0];
        for (int i = 1; i < n; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    private void countingSort(int[] array, int n, int i) {
        int max = getMax(array, array.length);
        int accurate = (int)Math.floor(Math.log(max));
    
        int[] temp = new int[n];
        int[] count = new int[accurate];

        for (int j = 0; j < n; j++) {
            count[(array[j] / i) % accurate]++;
        }

        for (int j = 1; j < accurate; j++) {
            count[j] += count[j - 1];
        }

        for (int j = n - 1; j >= 0; j--) {
            temp[count[(array[j] / i) % accurate] - 1] = array[j];
            count[(array[j] / i) % accurate]--;
            // System.err.println("swap: array[j] = array[temp]");
            counterS++;
        }

        System.arraycopy(temp, 0, array, 0, n);
    }
}
