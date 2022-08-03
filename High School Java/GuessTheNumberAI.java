import java.util.Scanner;

public class GuessTheNumberAI {
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

        while (game) {
            // System.out.println(counter);

            if (guess < selected) {
                guess++;
                // guess = guess * 2;
                System.out.println(guess);
            }

            if (guess > selected) {
                // guess--;
                guess = guess / 2;
                System.out.println(guess);
            }

            // ////////
            if (guess == selected) {
                game = false;
                // System.exit(0);
            }
            if (selected == guess) {
                game = false;
                // System.exit(0);
            }
            counter++;
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // ///////////
        }
        System.out.println("guess is: " + guess);
        System.out.println("counter: " + counter);
    }
}
