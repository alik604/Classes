import java.util.Scanner;

public class geussthenumbAI_withHelp {

	public static void main(String[] args) {

		boolean game = true;
		Scanner scan = new Scanner(System.in);

		System.out.println("select number between 1-100");
		int selected = scan.nextInt();

		if (selected > 100 || selected < 1) {
			System.out.println("error");
			System.exit(0);
		}
		System.out.println("selected is:" + selected);

		int guess = 75;
		int counter = 1;

		// Init
		int gMin = 0;
		int gMax = 100;
		int gNext = 50;

		// Guess
	//	doGuess(gNext);
		while(gNext != selected){
		guess = gNext;
		if (guess < selected) {
			if (guess > gMin)
				gMin = guess;

		} else if (guess > selected) {
			if (guess < gMax)
				gMax = guess;
		}

		gNext = ((int) Math.ceil((gMax - gMin) / 2f)) + gMin;
		counter++;
}
		if (gNext == selected)
			System.out.println("yay! it works");
		System.out.println(counter);

	}

}
