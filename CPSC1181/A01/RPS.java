//       clear; del *.class; javac RPS*.java; java -ea RPSTester
package A01;

import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <h1>A game of Rock, paper, scissors.</h1>
 * 
 * @author Khizr Pardhan | 100282129
 * @version 1.0
 * @since 2017-05-15 10:00pm
 * @see https 
 *      ://d2l.langara.bc.ca/d2l/le/content/88736/viewContent/1305618/View?ou
 *      =88736
 */
public class RPS {
	public final static int ROCK = 0;
	public final static int PAPER = 1;
	public final static int SCISSORS = 2;
	private final static String[] CHOICES = { "ROCK", "PAPER", "SCISSORS" };

	private final static Random rand = new Random();

	public static void main(String[] args) {
		if (args.length > 0) {
			reset(Integer.parseInt(args[0]));
		}
		playGame(new Scanner(System.in));
	}

	/**
	 * Determines the winner of a round of RPS.
	 * 
	 * @param a
	 *            Player a's choice of R, P, or S
	 * @param b
	 *            Player b's choice of R, P, or S
	 * @return 1 if player a's choice beats player b, -1 if player b's choice
	 *         beats player a's choice, 0 if there is a draw (when both players
	 *         make the same choice)
	 */
	public static int determineWinner(int a, int b) {
		// determines the winner of a round of RPS
		// if a beats b, return 1
		// if b beats a, return -1
		// otherwise return 0
		if (a == b) // if draw return 0
			return 0;
		else if (b == 2 && a == 0)// Handle special case
			return 1;
		else if (a == 2 && b == 0)// Handle special case
			return -1;
		else if (a > b)// Handle general case
			return 1;
		else
			return -1;
	}

	/**
	 * Whether char array contain a "key"/certain target value
	 * 
	 * @param c
	 *            "key"/certain target value
	 * @param a
	 *            char array
	 * @return boolean value. true if key is found in array, false if it not
	 *         found
	 */
	public static boolean contains(char c, char[] a) {
		// assert false;
		// determines if a given character is in the array
		// String str = Character.toString(c);
		/*
		 * Convert char array to string, and use built in contains method to
		 * check is "key" (a) is present
		 */
		if (new String(a).contains(Character.toString(c)))// :)
			return true;

		return false;
	}

	/**
	 * Prompts user for an input that matches one of the given characters.
	 * repeats till valid inputer. Handles validation with help of @see contains
	 * 
	 * @param prompt
	 *            Prompt to user, such as an instruction or user's name
	 * @param options
	 *            char array
	 * @param sc
	 *            Scanner object
	 * @return filtered input from scanner object @see sc
	 */
	public static char getInput(String prompt, char[] options, Scanner sc) {
		// assert false;
		// prompts user for an input that matches one of the given characters
		// if its not one of those, repeat. (use contains (above))
		char ch;
		do {
			// prompt user
			System.out.print(prompt + " ( ");
			for (int i = 0; i < options.length - 1; i++) {
				System.out.print(options[i] + ", ");
			}
			System.out.print(options[options.length - 1] + " ):\n");

			// process input
			String temp = sc.next();
			ch = temp.charAt(0);// get input, convert to char

			// check if valid, if so return char, else loop till proper value
			if (contains(ch, options) && temp.length() == 1) /* check if valid */
				return ch;// return value and end

		} while (true);

	}

	/**
	 * Request the user for a choice: r,p,s (using @see getInput). calls @see
	 * makeChoice to get the computer's choice determine & return the winner
	 * (using @see determineWinner)
	 * 
	 * @param sc
	 *            Scanner object containing input
	 * 
	 * @return char 'p' if player wins, or 'c' if computer wins
	 */
	public static char playRound(Scanner sc) {
		// assert false;
		// ask the user for a choice: r,p,s (use getInput)
		// call makeChoice to get the computer's choice
		// determine & return the winner (use determineWinner)
		int choice = makeChoice();
		char[] myCharArray = new char[] { 'r', 'p', 's', 'q' };
		/*
		 * could have used this as Anonymous array, but i find it hard to debug
		 * and ugly to look at
		 */
		char ch = getInput("Choose", myCharArray, sc);
		if (ch == 'q')// Handle quit first
			return ch;
		int i = determineWinner(new String(myCharArray).indexOf(ch), choice);
		// print computer choice
		String str = "";
		switch (choice) {
		case 0:
			str = "ROCK";
			break;
		case 1:
			str = "PAPER";
			break;
		case 2:
			str = "SCISSORS";
			break;
		}
		System.out.println("Computer chose: " + str);
		// print player choice
		str = "";
		switch (ch) {
		case 'r':
			str = "ROCK";
			break;
		case 'p':
			str = "PAPER";
			break;
		case 's':
			str = "SCISSORS";
			break;
		}
		System.out.println("  Player chose: " + str);
		// self explanatory
		if (i == 1) {
			System.out.println("Player scores!");
			return 'p';
		} else if (i == -1) {
			System.out.println("Computer scores!");
			return 'c';
		} else {// if (i == 0)
			System.out.println("Draw!");
			return playRound(sc);
		}
	}

	/**
	 * when called prompts user for maximum score to win. displays score. runs
	 * game play (basically a control hub for program)
	 * 
	 * 
	 * @param sc
	 *            Scanner object containing input
	 * 
	 * @return char 'p' if player wins, or 'c' if computer wins, or 'q' if
	 *         quitting
	 */
	public static char playGame(Scanner sc) {// make p,c and pointsToWin global
												// and run
												// games in playRound??
		// assert false;
		// a game consists of a number of rounds
		// prompt the user for a maximum score to win

		// variable to hold player, and computer scores
		int p = 0;
		int c = 0;
		// // display and prompt points to win
		System.out.println("How many points to win?");
		int pointsToWin = sc.nextInt();
		System.out.println("Score: p=" + p + " c=" + c);
		// //^
		char myChar = playRound(sc);
		if (myChar == 'p') {
			p++;
			System.out.println(p > c ? "Player wins! " + p + " : " + c
					: "Computer wins! " + p + " : " + c);
			return 'p';
		} else if (myChar == 'c') {
			c++; // <-- LOL
			System.out.println(p > c ? "Player wins! " + p + " : " + c
					: "Computer wins! " + p + " : " + c);
			return 'c';
		} else {
			System.out.println("Quitting");
			return 'q';
		}
	}

	// DONT CHANGE THE METHODS BELOW. They work fine and are needed by the test
	// suite

	/**
	 * 
	 * reset random and set seed
	 * 
	 * @param seed
	 *            of type int
	 */
	public static void reset(int seed) {
		rand.setSeed(seed);
	}

	/**
	 * makes choice for computer
	 * 
	 * @return random.nextInt
	 */
	public static int makeChoice() {
		return rand.nextInt(CHOICES.length);
	}

	/**
	 * convert interger to one of "rock','paper',"scissor". based off given
	 * interger and corresponding index location in array(of length 3)
	 * 
	 * @param rps
	 *            int of 0,1,2
	 * 
	 * @return String "rock','paper',"scissor"
	 */
	public static String toString(int rps) {
		return CHOICES[rps];
	}

	/**
	 * given first letter of option, returns string of option(short form to full
	 * word)
	 * 
	 * @param c
	 *            char 'r','p','s'
	 * 
	 * @return corresponding string
	 */
	public static int mapToChoice(char c) {
		switch (c) {
		case 'r':
			return ROCK;
		case 'p':
			return PAPER;
		case 's':
			return SCISSORS;
		}
		assert false;
		return 'x'; // unreachable
	}
}