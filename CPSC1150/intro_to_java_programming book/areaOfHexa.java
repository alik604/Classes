package intro_to_java_programming;

import java.util.Scanner;

public class areaOfHexa {
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.println("enter side");
		double side = scan.nextDouble();

		double area = Math.sqrt(3) * 1.5 * side * side;
		System.out.println(area);

	}
}
