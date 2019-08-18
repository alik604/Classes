package intro_to_java_programming;

import java.util.Scanner;

public class quadFormula {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		double a = 1;
		double b = 2;
		double c = 1;
		final double disc = Math.sqrt(b * b - (4 * a * c));
		final double R1 = (-b + disc) / 2 * a;
		final double R2 = (-b - disc) / 2 * a;
		if (disc > 0) {
			System.out.println(R1);
			System.out.println(R2);
		} else if (disc == 0) {
			// same as: -b/2a
			System.out.println(R1);
		} else
			System.out.println("no roots");

	}

}
