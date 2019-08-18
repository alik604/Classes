package term3;

/**
 * program to factor a entered interger 
 * 
 * 
 */
import java.util.Scanner;

public class fact {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int open = 0;
		int numb=0;
		while (true) { // no need to relaunch app
			int factnumb = 2;
			System.out
					.println("please enter number which you wish to factor: ");
			try{
			 numb = scan.nextInt();}
			catch(Exception e){
				System.out.println("breaking out of loop...");
				break;
			}
			
			// int numb = 30;
			if (numb == 0)
				break;
			System.out.printf("Factors of " + numb + " are: 1"); // <-- not
																	// cheating...
			while (factnumb < numb) {
				if (numb % factnumb == 0) {
					// System.out.println(factnumb + " is a factor of " + numb);
					System.out.printf("," + factnumb);
				}
				factnumb++; // cant use "else", so i need while loop
			}
			System.out.println("");
		}
	}
}