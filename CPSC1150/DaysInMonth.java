package cpsc1150;

import java.util.Scanner;

public class DaysInMonth {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("please enter month: ");
		int month = scan.nextInt();

		System.out.println("please enter year: ");
		int year = scan.nextInt();
		int days = 0;
		boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));

		switch (month) {

		case 1:
			days = 31;
			break;
		case 2:
			days = 28; // had to do this due to weird error
			if (isLeapYear == true) {
				days = 29;
			}
			break;
		case 3:
			days = 31;
			break;
		case 4:
			days = 30;
			break;
		case 5:
			days = 31;
			break;
		case 6:
			days = 30;
			break;
		case 7:
			days = 31;
			break;
		case 8:
			days = 31;
			break;
		case 9:
			days = 30;
			break;
		case 10:
			days = 31;
			break;
		case 11:
			days = 30;
			break;
		case 12:
			days = 31;
			break;
		}
		System.out.println("days: " + days);
	}
}
