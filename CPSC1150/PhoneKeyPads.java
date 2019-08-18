package cpsc1150;

import java.util.Scanner;

public class PhoneKeyPads {
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		String input = "";
		do {
			System.out.printf("Enter a letter: ");
			input = scan.nextLine();// should have use the hint, and used
									// next(), that way i would not need the
									// loop
		} while (input.length() != 1);
		input = input.toLowerCase();

		char letter = input.charAt(0);

		if ((int) letter >= 97 && (int) letter <= 122) {
			// System.out.println("its a letter!: " + letter);
		} else
			System.out.println(letter + " is an invalid input");

		switch (letter) {
		case 'a':
		case 'b':
		case 'c':
			System.out.println("The corresponding number is 2");
			break;

		case 'd':
		case 'e':
		case 'f':
			System.out.println("The corresponding number is 3");
			break;

		case 'h':
		case 'g':
		case 'i':
			System.out.println("The corresponding number is 4");
			break;

		case 'j':
		case 'k':
		case 'l':
			System.out.println("The corresponding number is 5");
			break;

		case 'm':
		case 'n':
		case 'o':
			System.out.println("The corresponding number is 6");
			break;

		case 'p':
		case 'q':
		case 'r':
		case 's':
			System.out.println("The corresponding number is 7");
			break;

		case 't':
		case 'u':
		case 'v':
			System.out.println("The corresponding number is 8");
			break;

		case 'w':
		case 'x':
		case 'y':
		case 'z':
			System.out.println("The corresponding number is 9");
			break;

		}
	}
}
