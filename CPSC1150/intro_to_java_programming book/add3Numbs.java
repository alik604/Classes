package intro_to_java_programming;

import java.util.Scanner;

public class add3Numbs {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		int numb1 = (int) (System.currentTimeMillis() % 10);
		int numb2 = (int) (System.currentTimeMillis() / 7 % 10);
		int numb3 = (int) (System.currentTimeMillis() / 3 % 10);

		System.out.printf("what is %d + %d + %d:", numb1, numb2, numb3);

		if (scan.nextInt() == numb1 + numb2 + numb3)
			System.out.println("nigga you right!");

	}
}
