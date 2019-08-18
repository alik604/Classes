import java.util.Scanner;

public class quickMaths {
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		int a;
		int b;
		int c;
		while (true) {
			a = getRand(3, 9);
			b = getRand(3, 9);
			c = a * b;

			System.out.println(a + "*" + b + " is?");

			if (c == scan.nextInt()) {
				System.out.println("good job!");
				continue;
			}
			System.out.println("nope :'( ");

		} // while true loop
	}

	public static int getRand(int minimum, int maximun) {
		 maximun= maximun - minimum;
		maximun++;// include max ---> [min..max]
		return minimum + (int) (Math.random() * maximun);

	}
}
