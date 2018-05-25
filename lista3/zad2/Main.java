import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class Main {

	public static void main(String[] args) {

		if (!(args[0].equals("-p") || args[0].equals("-r"))) {
			System.exit(0);
		}

		Scanner input = new Scanner(System.in);

		int n = input.nextInt();
		int k = input.nextInt();
		input.close();
		int[] array = createArray(n, args[0]);

		System.out.println("\nk = " + k);

		int[] array_select_rdn = Arrays.copyOf(array, n);
		RandomSelect random = new RandomSelect();
		System.out.println("\nRANDOM SELECT");
		random.random_selection(array_select_rdn, 0, n - 1, k);
		System.out.println("\n >>Comparisons: " + random.getCompareNumber() + " >>Swaps: " + random.getSwapNumber());
		output(n, k, array_select_rdn);

		System.out.println("\n----------------------------------\n");

		int[] array_select = Arrays.copyOf(array, n);
		Select select = new Select();
		System.out.println("\nSELECT");
		select.selection(array_select, k);
		System.err.println("\n >>Comparisons: " + select.getCompareNumber() + " >>Swaps: " + select.getSwapNumber());
		output(n, k, array_select);
	}


	private static int[] createArray(int n, String type) {
		int[] array = new int[n];
		if (type.equals("-p")) {
			for (int i = 0; i < n; i++)
				array[i] = i;

			List<Integer> list = new ArrayList<>();
			for (int i : array) {
				list.add(i);
			}
			Collections.shuffle(list);

			for (int i = 0; i < list.size(); i++) {
				array[i] = list.get(i);
			}
		}
		if (type.equals("-r")) {
			Random generator = new Random();

			for (int i = 0; i < n; i++) {
				array[i] = generator.nextInt(n);
			}
		}

		for (int i = 0; i < n; i++)
			System.out.print(array[i] + " ");
		return array;
	}

	private static void output(int n, int k, int[] array) {
		for (int i = 0; i < n; i++) {
			if (i == k) {
				System.out.print("[" + array[i] + "] ");
			} else {
				System.out.print(array[i] + " ");
			}
		}
	}
}

class RandomSelect {

	private int swapNumber;
	private int comparisonsNumber;

	int random_selection(int[] array, int begin, int end, int k) {

		if (begin == end) {
			return array[begin];
		}

		if (k > 0 && k <= end - begin + 1) {

			int index = partition(array, begin, end);
			if (index - begin == k - 1) {
				return array[index];
			}
			if (index - begin > k - 1) {
				return random_selection(array, begin, index - 1, k);
			}
			// else
			return random_selection(array, index + 1, end, k - index + begin - 1);
		}
		return -1;
	}

	private int partition(int[] array, int left, int right) {
		Random generator = new Random();
		int end = array[generator.nextInt(array.length-1)];
        // System.err.println(">>pivot<<: " + end);
		int idX = left;
		for (int j = left; j <= right - 1; j++) {

            // System.err.println(">>compare: " + array[j] + " " + end);
			comparisonsNumber++;

			if (array[j] <= end) {

                // System.err.println(">>swap: " + array[j] + " " + array[idX]);
				swapNumber++;

				int temp = array[idX];
				array[idX] = array[j];
				array[j] = temp;
				idX++;
			}
		}

        // System.err.println("swap: " + array[idX] + " " + array[right]);
		swapNumber++;

		int temp = array[idX];
		array[idX] = array[right];
		array[right] = temp;
		return idX;
	}

	int getCompareNumber() {
		return comparisonsNumber;
	}

	int getSwapNumber() {
		return swapNumber;
	}
}

class Select {

	private int swapNumber;
	private int comparisonsNumber;

	void selection(int[] array, int k) {
		select(array, 0, array.length - 1, (k - 1));
	}

	private int select(int[] array, int left, int right, int k) {

		int n = right - left + 1;

		if (n <= 1)
			return array[left];

		double length = (double) n / 5;
		int[] Median = new int[(int) Math.ceil(length)];

		int middle = 0;

		for (int l = left; l < right + 1; l += 5) {
			int low = l;
			int high = Math.min(l + 5, right + 1);
			int partition[] = new int [high - low];

			int idX = 0;
			for (int j = low; j < high; j++) {
				partition[idX] = array[j];
				idX++;
			}

			sort(partition);

			double median;
			if (partition.length % 2 == 0)
				median = ((double)partition[partition.length/2] + (double)partition[partition.length/2 - 1])/2;
			else
				median = (double) partition[partition.length/2];

			Median[middle] = (int)median;
			middle++;
		}

		int x = select(Median, 0, Median.length - 1, (Median.length-1) / 2);
		int r = partition(array, left, right, x);
		int i = r - left + 1;

		if (k==i) {
			return x;
		} else if (k < i) {
			return select(array, left, r - 1, k);
		} else {
			return select(array, r+1, right, k - i);
		}
	}

	void sort(int arr[]) {
		int n = arr.length;
		for (int i=1; i<n; ++i)
		{
			int key = arr[i];
			int j = i-1;

			while (j>=0 && arr[j] > key)
			{
				arr[j+1] = arr[j];
				j = j-1;
			}
			arr[j+1] = key;
		}
	}

	private int partition (int [] list, int p, int r, int x) {
		int temp = 0;
		int pivotindex = 0;
		for (int i=0; i < list.length; i++) {
			if (list[i] == x) {
				pivotindex = i;
				break;
			}
		}

		// System.err.println("swap: " + list[pivotindex] + " " + list[r]);
		swapNumber++;

		temp = list[pivotindex];
		list[pivotindex] = list[r];
		list[r] = temp;

		int i = p - 1;

		for (int j = p; j < r; j++) {
			if (list[j] < x) {

				// System.err.println(">>compare: " + list[j] + " " + x);
				comparisonsNumber++;

				// System.err.println("swap: " + list[i] + " " + list[j]);
				swapNumber++;

				i++;
				temp = list[i];
				list[i] = list[j];
				list[j] = temp;
			}
		}

		// System.err.println("swap: " + list[i+1] + " " + list[r]);
		swapNumber++;

		temp = list[i+1];
		list[i+1] = list[r];
		list[r] = temp;

		return i+1;
	}

	int getCompareNumber() {
		return comparisonsNumber;
	}

	int getSwapNumber() {
		return swapNumber;
	}
}