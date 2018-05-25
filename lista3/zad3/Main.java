import java.util.Scanner;

import static java.lang.Integer.parseInt;


class BinarySearch {
	public static int compare = 0;

	int binarySearch(int array[], int left, int right, int value) {
		if (right >= left) {
			int mid = left + (right - left) / 2;
			compare++;
			if (array[mid] == value)
				return 1;
			compare++;
			if (array[mid] > value)
				return binarySearch(array, left, mid - 1, value);
			return binarySearch(array, mid + 1, right, value);
		}
		return 0;
	}

	int getCompare() {
		int comp = compare;
		compare = 0;
		return comp;
	}
}

public class Main {

	public static void main(String args[]) {
		// manual(args);
		auto();
	}

	public static void auto() {
		BinarySearch search = new BinarySearch();
		long startTime, estimatedTime;
		int result, i = 0;
		int[] array;
		
		for (int length = 1000; length < 100000; length+=1000) {
			array = new int[length];
			for (i = 0; i < length; i++)
				array[i] = i;
			startTime = System.nanoTime();
			result = search.binarySearch(array, 0, length - 1, 1);
			estimatedTime = System.nanoTime() - startTime;
			int compares = search.getCompare();
			System.out.println("Compare: " + compares);
			double logar = (Math.log(length)/Math.log(2));
			System.out.println(compares/logar);
			System.out.println("Time: " + estimatedTime);
		}
	}

	public static void manual(String args[]) {
		if (args[0].equals("--v")) {
			if (args[2].equals("--n")) {
				int value = parseInt(args[1]);
				int length = parseInt(args[3]);
				int[] array = new int[length];
				// Scanner input = new Scanner(System.in);
				// for (int i = 0; i < length; i++)
				// 	array[i] = input.nextInt();

				for (int i = 0; i < length; i++)
					array[i] = i;

				BinarySearch search = new BinarySearch();

				long startTime = System.nanoTime();
				int result = search.binarySearch(array, 0, length - 1, value);
				long estimatedTime = System.nanoTime() - startTime;

				System.out.println("Compare: " + search.getCompare());
				System.out.println("Time: " + estimatedTime);
				if (result == 0)
					System.out.println("no");
				else
					System.out.println("yes");
			}
		}
	}
}