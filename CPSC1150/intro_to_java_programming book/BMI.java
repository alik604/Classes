package intro_to_java_programming;

import java.util.Scanner;

public class BMI {
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		System.out.println("enter wieght in pounds");
		double kg = 0.45359237 * scan.nextDouble();
		System.out.println("enter height in inch");
		double meters = 0.0254 * scan.nextDouble();
		double BMI = kg / Math.pow(meters, 2);
		System.out.println(BMI);

	}
}
