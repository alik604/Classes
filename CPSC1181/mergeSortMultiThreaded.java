import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class mergeSortMultiThreaded {

	/**
	 * Is it "worth" me having a 2nd thread. (or should t2's stuff be done my main
	 * thread
	 * 
	 * 
	 */
	static int[] part1 = new int[50]; // no recursions of "mergeSortMultiThreaded" so this should be fine
	static int[] part2 = new int[50];

	public static void main(String[] args) {

		Random rand = new Random();

		int[] A = new int[100];

		for (int i = 0; i < A.length; i++) {
			A[i] = rand.nextInt(1001);

		}

		System.out.println("\nBefore Soring :");
		printA(A);
		// System.out.println("\nThis is currently running on: " +
		// Thread.currentThread().getId());

		// (src , src-offset , dest , offset, count)
		System.arraycopy(A, 0, part1, 0, part1.length);
		System.arraycopy(A, part1.length, part2, 0, part2.length);

		System.out.println("\n\nAfer spliting1 :");
		printA(part1);
		System.out.println("\n\nAfer spliting2 :");
		printA(part2);

		part1 = merge_sort(part1);

		Thread t2 = new Thread(() -> {
			part2 = merge_sort(part2);
		});

		try {
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ------ working below ---------------
		A = merge(part1, part2);
		System.out.println("\n\nAfter Sorting :");
		printA(A);

	}

	public static int[] merge_sort(int[] B) {
		if (B.length <= 1) {
			return B;
		}
		int midpoint = B.length / 2;
		int[] left = new int[midpoint];
		int[] right;
		int[] result = new int[B.length];

		if (B.length % 2 == 0) {
			right = new int[midpoint];
		} else {
			right = new int[midpoint + 1];
		}

		for (int i = 0; i < midpoint; i++) {
			left[i] = B[i];
		}
		int x = 0;
		for (int j = midpoint; j < B.length; j++) {

			if (x < right.length) {
				right[x] = B[j];
				x++;
			}
		}

		left = merge_sort(left);
		right = merge_sort(right);

		result = merge(left, right);
		return result;
	}

	public static int[] merge(int[] left, int[] right) {
		int lenghtresult = left.length + right.length;
		int[] result = new int[lenghtresult];
		int indexL = 0;
		int indexR = 0;
		int indexRes = 0;

		while (indexL < left.length || indexR < right.length) {
			if (indexL < left.length && indexR < right.length) {
				if (left[indexL] <= right[indexR]) {

					result[indexRes] = left[indexL];
					indexL++;
					indexRes++;
				} else {
					result[indexRes] = right[indexR];
					indexR++;
					indexRes++;
				}
			} else if (indexL < left.length) {
				result[indexRes] = left[indexL];
				indexL++;
				indexRes++;
			} else if (indexR < right.length) {
				result[indexRes] = right[indexR];
				indexR++;
				indexRes++;
			}
		}
		return result;

	}

	private static void printA(int[] B) {
		for (int i = 0; i < B.length; i++) {
			System.out.print(B[i] + " , ");
		}

	}

}// class
