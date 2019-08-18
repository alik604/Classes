// [min..max) (include min, exclude max)
public class randnumbersInRange {
	public static void main(String[] args) {
		int minimum = 5;
		int maximum = 10 - minimum;
		// maximum++;// include max ---> [min..max]
		int randomNum = minimum + (int) (Math.random() * maximum);
		System.out.println(randomNum);

	}
}
