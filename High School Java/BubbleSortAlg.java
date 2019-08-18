package term3;

import java.util.Arrays;

/**
 * @author kali
 * 
 *         double for loop concept is not mine, i was aim for an efficent way.
 * 
 *         i want original string to be colored red, but i get formating errors
 *         in console once every few times i compile
 */
public class BubbleSortAlg {
	public static void main(String args[]) {
		double letstryD = 6.2;
		int letstryI = (int) letstryD; // me playing around with casting
		int[] sortnumb = { (int) Math.ceil(letstryD), letstryI, (int) letstryD,
				4, 66, 4, 0, 44, 33, 27, 85, 99, -11, 100 + 134, 200 * 2, 100 };
		System.out.println("default numbs to sort are");
		for (int arrange = 0; arrange < sortnumb.length; arrange++)
			System.err.print(sortnumb[arrange] + ","); // red text

		int[] sortnumb2 = sortnumb;
		// System.out.println();
		// for (int arrange = 0; arrange < sortnumb.length; arrange++)
		// System.out.print(sortnumb2[arrange] + ","); // red text

		System.out.println("\n\n"); // double space
		int copy = 0; // swift to lower left or right
		for (int first = 0; first < sortnumb.length - 1; first++)
			for (int sec = 0; sec < sortnumb.length - 1; sec++)
				// (int sec = 0; sec < first; sec++) -- this also works
				if (sortnumb[sec] > sortnumb[sec + 1]) {
					// if left numb it greater then right #
					copy = sortnumb[sec]; // clone left to copy
					sortnumb[sec] = sortnumb[sec + 1]; // make left = right
					sortnumb[sec + 1] = copy; // make right = copy
				}
		for (int i = 0; i < sortnumb.length; i++)
			System.out.print(sortnumb[i] + ",");
		System.out.println("\none I made  ^---------------------------------");
		double a = Math.round(Math.random() * 10); // 10 gives 1 digi, 100 give
													// 2 digi
		// int b = (int) a;
		int c = (int) Math.round(Math.random() * 100); // <-- clear insanity
		System.out.println("changed 400 to: " + c);
		sortnumb[15] = c; // to visually prove its not the same
		Arrays.sort(sortnumb2);
		for (int i = 0; i < sortnumb2.length; i++)
			System.out.print(sortnumb2[i] + ",");
		System.out.println("\narrays.sort ^---------------------------------");
	}
}