/**
 * Created by Khizr ali pardhan on 2/29/2016.
 * 2 in one binary to integer and
 * integer to binary converter.
 */
package term2;

import java.util.Scanner;

public class konverter {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("please type 1 for binary to decimal converter, else type 2 or any alphanumeric characters (0 to quit): ");
            // cant use 000 to quit lily originally planned, avoiding a
            // parse-ing nightmare

            try {
                int type = scan.nextInt();
                if (type == 0) { // / 000 is 0 because this is an integer
                    System.out.println("Programm quit");
                    break;
                }
                if (type == 1) {
                    BinToInt();
                }
                if (type == 2) {
                    IntToBin();
                }

            } catch (Exception e) {
                // e.printStackTrace();
                // if letter is entered, int to bin method will trigger
                // number other then 0,1,2 followed by letters gives problems.
                // using else statement gaves bigger problems
                IntToBin();
            }
        } while (true);
        scan.close();
    }

    public static void IntToBin() {

        Scanner i2b = new Scanner(System.in);
        System.out.println("please enter a int you wish to convert to binary");
        int dec = i2b.nextInt();
        if (dec > Integer.MAX_VALUE || dec < 0) {
            throw new ArithmeticException("Error with Integer!");
        }
        /**
         * System.out.println("The int converter to Binary is: " +
         * Integer.toBinaryString(i));
         * System.out.println("The int converter to Hex is: " +
         * Integer.toString(i, 16));
         */
        String s = "";
        for (int c = 0; c < 31; c++) { // c < 8 for 256 c < 31 for max int value

            if (dec % 2 == 1) {
                s = '1' + s;
            }
            if (dec % 2 == 0) {
                s = '0' + s;
            }
            dec = dec / 2;
        }

        System.out.println("The int converter to Binary is: " + s);
    }

    public static void BinToInt() {

        Scanner b2i = new Scanner(System.in);
        System.out.println("please enter a binary value you wish to convert to an int");
        String s = b2i.nextLine();

        if (s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9")) {
            System.out.println("please enter ones and zeros only");
            // System.exit(0); screws up do while loop
        }

        int foo = Integer.parseInt(s, 2);
        System.out.println(foo);

        /*
         * int bin = b2i.nextInt(); int dec = 0; while (bin != 0) { int digit =
         * bin % 10; dec = dec * 2 + digit; bin = bin / 10; }
         * System.out.println("Decimal: " + dec);
         */

        // int myInt = Integer.parseInt(s);

        // gives error with index
        /*
         * int x = 128; int total = 0; System.out.println("s is " + s); for (int
         * j = 0; j < 8; j++) { if (s.charAt(j) == '1') { total = total + (x *
         * 1); } x = x / 2; } System.out.println(total);
         */

    }
}
