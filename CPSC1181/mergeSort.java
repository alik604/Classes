import java.util.Random;

public class mergeSort {
	public static void main(String[] args) {

		Random rand = new Random();

		int[] A = new int[1000];

		for (int i = 0; i < A.length; i++) {
			A[i] = rand.nextInt(1001);

		}

		System.out.println("Before Soring :");

		printA(A);
		A = merge_sort(A);
		System.out.println("\n\nAfter Sorting :");
		printA(A);

	}// System.out.println(arry[i]);

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
		
		System.out.println("----------------------");
		for (int i = 0; i < B.length; i++) {
			System.out.print(B[i] + " , ");
		}

	}

}// class
