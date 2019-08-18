package cpsc1150;

import java.util.Scanner;

public class ArithmeticCalculator {
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		System.out.println("Enter 1st number: ");
		double numb1 = scan.nextDouble();
		System.out.println("Enter operator: ");
		String op = scan.next();
		System.out.println("Enter 2nd number: ");
		double numb2 = scan.nextDouble();
		double sum = 0;
		switch (op) {
		case "+":
			sum = numb1 + numb2;
			break;

		case "*":
			sum = numb1 * numb2;
			break;

		case "/":
			sum = numb1 / numb2;
			break;

		case "-":
			sum = numb1 - numb2;
			break;

		default:
			System.out.println("Invalid operator!");
			System.exit(0);
		}

		System.out.printf("%.2f %s %.2f = %f", numb1, op, numb2, sum);
	}
}
