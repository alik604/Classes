package term3;

import java.util.Random;
import java.util.Scanner;

/**
 * @author kali - there will be 4 "pegs", number randomly between 1-6, 8
 *         Attempts given. - if user hits worng digi, they will be stuck will
 *         their choice - if user hits non digi key, program will end -i hope i
 *         made this game with the proper rules
 */
public class MasterMind {
	public static void main(String[] args) {
		Random ran = new Random();
		Scanner sc = new Scanner(System.in);

		int guess = 1;
		int hit_counter = 0;

		int peg1 = 1 + ran.nextInt(6);
		int peg2 = 1 + ran.nextInt(6);
		int peg3 = 1 + ran.nextInt(6);
		int peg4 = 1 + ran.nextInt(6);

		int chkpeg1 = 0;
		int chkpeg2 = 0;
		int chkpeg3 = 0;
		int chkpeg4 = 0;

		System.out.println("peg 1 is: " + peg1);
		System.out.println("peg 2 is: " + peg2);
		System.out.println("peg 3 is: " + peg3);
		System.out.println("peg 4 is: " + peg4);
		System.out.println("");
		while (guess <= 8) {// 13 i think, i can't count :'(
			try {
				System.out.println("guess peg 1:");
				chkpeg1 = sc.nextInt();
				if (chkpeg1 >= 1 && chkpeg1 <= 6) {
				} else {
					System.out.println("error invalid digit");
				}
				System.out.println("guess peg 2:");
				chkpeg2 = sc.nextInt();
				if (chkpeg2 >= 1 && chkpeg2 <= 6) {
				} else {
					System.out.println("error invalid digit");
				}
				System.out.println("guess peg 3:");
				chkpeg3 = sc.nextInt();
				if (chkpeg3 >= 1 && chkpeg3 <= 6) {
				} else {
					System.out.println("error invalid digit");
				}
				System.out.println("guess peg 4:");
				chkpeg4 = sc.nextInt();

				if (chkpeg4 >= 1 && chkpeg4 <= 6) {
				} else {
					System.out.println("error invalid digit");
				}
			} catch (Exception e) {
				System.out.println("Invalid char");
				break;
			}
			if (peg1 == chkpeg1) {
				hit_counter++;
				if (hit_counter == 1) {
					System.out.printf("Hit Counter: ");
				}
				System.out.printf("*");
			}

			if (peg2 == chkpeg2) {
				hit_counter++;
				if (hit_counter == 1) {
					System.out.printf("Hit Counter: ");
				}
				System.out.printf("*");
			}
			if (peg3 == chkpeg3) {
				hit_counter++;
				if (hit_counter == 1) {
					System.out.printf("Hit Counter: ");
				}
				System.out.printf("*");
			}
			if (peg4 == chkpeg4) {
				hit_counter++;
				if (hit_counter == 1) {
					System.out.printf("Hit Counter: ");
				}
				System.out.printf("*");
			}
			if (hit_counter == 4) {
				System.out
						.println("\nyou win... or you cheated (I've got trust issues)");
				break;
			}
			System.out.println();
			System.out.println("current number of failed attemps is: " + guess);
			guess++;
			hit_counter = 0;
		}// while loop

		System.out.println("end of loop");
	}
}
