package intro_to_java_programming;

import java.util.Scanner;

public class windChillTemp {
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		double Ta, V, Twc;
		Ta = V = Twc = 0;

		System.out.println("enter temp ");
		Ta = scan.nextDouble();
		System.out.println("enter wind speed");
		V = scan.nextDouble();

		Twc = 35.74 + (0.6215 * Ta) - (35.75 * Math.pow(V, 0.16)) + 0.4275 * Ta
				* Math.pow(V, 0.16);

		System.out.println(Twc);

	}
}
