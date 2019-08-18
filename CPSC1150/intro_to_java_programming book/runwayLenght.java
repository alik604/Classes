package intro_to_java_programming;

import java.util.Scanner;

public class runwayLenght {
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.println("please enter speed");
		double speed = scan.nextDouble();

		System.out.println("please enter acceleration");
		double acc = scan.nextDouble();

		double lenght = Math.pow(speed, 2) / (2 * acc);

		System.out.println(lenght);

	}
}
