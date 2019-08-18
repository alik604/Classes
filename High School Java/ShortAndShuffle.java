package selfTeach;

/**
 * @author kali
 *
 *is my sort method efficient?
 *can i make myShuffle "better"?
 */
public class ShortAndShuffle {
	public static void main(String[] args) {

		int[] myArray = { 2, 3, 1, 4, 6, 5, 7, 9, 8, 0 };
		boolean bigToSamll = true;
		System.out.println("Original");
		for (int i = 0; i < myArray.length; i++)
			System.out.print(myArray[i]);

		System.out.println("\nShuffle (not my work)");
		shuffle(myArray);
		for (int i = 0; i < myArray.length; i++)
			System.out.print(myArray[i]);

		System.out.println("\nSort big - small");
		sort(myArray, bigToSamll);
		for (int i = 0; i < myArray.length; i++)
			System.out.print(myArray[i]);

		System.out.println("\nSort small - big");
		sort(myArray, !bigToSamll);
		for (int i = 0; i < myArray.length; i++)
			System.out.print(myArray[i]);

		System.out.println("\nmyShuffle");
		myShuffle(myArray);
		for (int i = 0; i < myArray.length; i++)
			System.out.print(myArray[i]);

	}

	public static int[] shuffle(int[] array) {
		int i = 0;
		int temp = 0;
		int j = 0;
		for (i = array.length - 1; i > 0; i -= 1) {
			j = (int) (Math.floor(Math.random() * (i + 1)));
			temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;

	}

	public static int[] sort(int[] array, boolean BS) {// BS = big to small
		int i = 0;
		int temp = 0;
		int j = 0;
		if (BS)
			for (j = 0; j < array.length - 1; j++)
				for (i = array.length - 1; i > 0; i -= 1) {
					if (array[i] > array[i - 1]) {
						temp = array[i];
						array[i] = array[i - 1];
						array[i - 1] = temp;
					}

				}
		else {

			for (j = 0; j < array.length - 1; j++)
				for (i = array.length - 1; i > 0; i -= 1) {
					if (array[i] < array[i - 1]) {
						temp = array[i];
						array[i] = array[i - 1];
						array[i - 1] = temp;
					}

				}

		}
		return array;
	}

	public static int[] sortSB(int[] array) {
		int i = 0;
		int temp = 0;
		int j = 0;
		for (j = 0; j < array.length - 1; j++)
			for (i = array.length - 1; i > 0; i -= 1) {
				if (array[i] < array[i - 1]) {
					temp = array[i];
					array[i] = array[i - 1];
					array[i - 1] = temp;
				}

			}
		return array;
	}

	// //^ unused see sort
	public static int[] myShuffle(int[] array) {
		int i = 0;
		int temp = 0;
		int j = 0;

		for (i = 0; i < array.length; i++) {
			j = (int) Math.floor(Math.random() * i);
			temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}

		return array;
	}
}
/*
 * int currentIndex = array.length; int temporaryValue = 0; int randomIndex = 0;
 * 
 * // While there remain elements to shuffle... while (0 != currentIndex) {
 * 
 * // Pick a remaining element... randomIndex = (int) (Math.floor(Math.random()
 * * currentIndex)); currentIndex -= 1;
 * 
 * // And swap it with the current element. temporaryValue =
 * array[currentIndex]; array[currentIndex] = array[randomIndex];
 * array[randomIndex] = temporaryValue; }
 */
