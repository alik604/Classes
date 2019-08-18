package intro_to_java_programming;

import java.util.Scanner;

public class calculatingEnergy {
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.println("please enter water in kg");
		double M = scan.nextDouble();

		System.out.println("ent int temp");
		double intTemp = scan.nextDouble();

		System.out.println("fin temp");
		double finTemp = scan.nextDouble();

		double Q = M * (finTemp - intTemp) * 4184;

		System.out.println(Q);

	}
}
