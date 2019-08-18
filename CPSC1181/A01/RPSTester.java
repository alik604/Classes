// del *.class; javac RPS*.java; java -ea RPSTester
package A01;

import java.util.*;
import java.io.*;

public class RPSTester {

    static {
        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (!assertsEnabled) {
           throw new RuntimeException("Asserts must be enabled!!! java -ea "
              + RPSTester.class.getName());
        }
    }

    private final static String LINE_SEP = System.getProperty("line.separator");

    public static void main(String[] args) {
        testMakeChoice();
        testMapToChoice();
		testDetermineWinner();
		testContains();
		testGetInput();
		testPlayRound();
		testPlayGame_quit();
        testPlayGame_to1();
        testPlayGame_to3();
        System.err.println("PASS");
    }

    private static void testMapToChoice() {
        assert RPS.mapToChoice('r') == RPS.ROCK;
        assert RPS.mapToChoice('p') == RPS.PAPER;
        assert RPS.mapToChoice('s') == RPS.SCISSORS;
    }

    private static void testDetermineWinner() {
        int[][] combos = {
            {0, RPS.ROCK, RPS.ROCK}, {-1, RPS.ROCK, RPS.PAPER}, {1, RPS.ROCK, RPS.SCISSORS},
            {1, RPS.PAPER, RPS.ROCK}, {0, RPS.PAPER, RPS.PAPER}, {-1, RPS.PAPER, RPS.SCISSORS},
            {-1, RPS.SCISSORS, RPS.ROCK}, {1, RPS.SCISSORS, RPS.PAPER}, {0, RPS.SCISSORS, RPS.SCISSORS}
        };

        for(int[] combo : combos) {
            assert combo[0] == RPS.determineWinner(combo[1], combo[2]) :
                RPS.toString(combo[1]) + " " + RPS.toString(combo[2]);
        }
    }

    /** test for determining if a char is in a char[] */
    private static void testContains() {
        char[] a = { 'b', 'x', 'y', 'z', 'k', 'q', 'x'};

        for(char c : a) {
            assert RPS.contains(c, Arrays.copyOf(a, a.length));
        }
        assert !RPS.contains('a', Arrays.copyOf(a, a.length));
        assert !RPS.contains('c', Arrays.copyOf(a, a.length));
        assert !RPS.contains('f', Arrays.copyOf(a, a.length));
        assert !RPS.contains('w', Arrays.copyOf(a, a.length));
        assert !RPS.contains('\n', Arrays.copyOf(a, a.length));
        assert !RPS.contains('\007', Arrays.copyOf(a, a.length));
    }

    private static void testGetInput() {
        OutputStream out;

    out = resetSystemOut();
    assert 'y' == RPS.getInput("Choose", new char[] {'y','n','q'}, new Scanner("y\n"));
    assertOutput("Choose ( y, n, q ):\n", out);
    out = resetSystemOut();
    assert 'n' == RPS.getInput("Alice", new char[] {'y','n','q'}, new Scanner("n\n"));
    assertOutput("Alice ( y, n, q ):\n", out);
    out = resetSystemOut();
    assert 'q' == RPS.getInput("Bob", new char[] {'y','n','q'}, new Scanner("q\n"));
    assertOutput("Bob ( y, n, q ):\n", out);
    out = resetSystemOut();
    assert 'q' == RPS.getInput("Cloe", new char[] {'y','n','q'}, new Scanner("x\nw\nq\n"));
    assertOutput("Cloe ( y, n, q ):\n" +
        "Cloe ( y, n, q ):\n" +
        "Cloe ( y, n, q ):\n", out);
    out = resetSystemOut();
    assert 'v' == RPS.getInput("Doug", new char[] {'v'}, new Scanner("vvvv\nv\n"));
    assertOutput("Doug ( v ):\n" +
        "Doug ( v ):\n", out);
    }

    private static void testPlayRound() {
        OutputStream out;

        RPS.reset(0);
        out = resetSystemOut();
        assert 'p' == RPS.playRound(new Scanner("p\n"));
        assertOutput("Choose ( r, p, s, q ):\n" +
            "Computer chose: ROCK\n" +
            "  Player chose: PAPER\n" +
            "Player scores!\n", out);
        out = resetSystemOut();
        assert 'c' == RPS.playRound(new Scanner("r\n"));
        assertOutput("Choose ( r, p, s, q ):\n" +
            "Computer chose: PAPER\n" +
            "  Player chose: ROCK\n" +
            "Computer scores!\n", out);
        out = resetSystemOut();
        assert 'p' == RPS.playRound(new Scanner("p\nr\n"));
        assertOutput("Choose ( r, p, s, q ):\n" +
            "Computer chose: PAPER\n" +
            "  Player chose: PAPER\n" +
            "Draw!\n" +
            "Choose ( r, p, s, q ):\n" +
            "Computer chose: SCISSORS\n" +
            "  Player chose: ROCK\n" +
            "Player scores!\n", out);
        assert 'q' == RPS.playRound(new Scanner("q\n"));
    }

    private static void testPlayGame_quit() {
        OutputStream out;

        RPS.reset(0);
        out = resetSystemOut();
        assert 'q' == RPS.playGame(new Scanner("1\nq\n"));
        assertOutput("How many points to win?\n" +
            "Score: p=0 c=0\n" +
            "Choose ( r, p, s, q ):\n" +
            "Quitting\n", out);
    }

    private static void testPlayGame_to1() {
        OutputStream out;

        RPS.reset(0);
        out = resetSystemOut();
        assert 'p' == RPS.playGame(new Scanner("1\np\n"));
        assertOutput("How many points to win?\n" +
            "Score: p=0 c=0\n" +
            "Choose ( r, p, s, q ):\n" +
            "Computer chose: ROCK\n" +
            "  Player chose: PAPER\n" +
            "Player scores!\n" +
            "Player wins! 1 : 0\n", out);

        RPS.reset(0);
        out = resetSystemOut();
        assert 'c' == RPS.playGame(new Scanner("1\ns\n"));
        assertOutput("How many points to win?\n" +
            "Score: p=0 c=0\n" +
            "Choose ( r, p, s, q ):\n" +
            "Computer chose: ROCK\n" +
            "  Player chose: SCISSORS\n" +
            "Computer scores!\n" +
            "Computer wins! 0 : 1\n", out);
    }

    private static void testPlayGame_to3() {
        OutputStream out;

    RPS.reset(0);
    out = resetSystemOut();
    assert 'p' == RPS.playGame(new Scanner("3\np\ns\nr\np\nr\n"));
    assertOutput("How many points to win?\n" +
        "Score: p=0 c=0\n" +
        "Choose ( r, p, s, q ):\n" +
        "Computer chose: ROCK\n" +
        "  Player chose: PAPER\n" +
        "Player scores!\n" +
        "Score: p=1 c=0\n" +
        "Choose ( r, p, s, q ):\n" +
        "Computer chose: PAPER\n" +
        "  Player chose: SCISSORS\n" +
        "Player scores!\n" +
        "Score: p=2 c=0\n" +
        "Choose ( r, p, s, q ):\n" +
        "Computer chose: PAPER\n" +
        "  Player chose: ROCK\n" +
        "Computer scores!\n" +
        "Score: p=2 c=1\n" +
        "Choose ( r, p, s, q ):\n" +
        "Computer chose: SCISSORS\n" +
        "  Player chose: PAPER\n" +
        "Computer scores!\n" +
        "Score: p=2 c=2\n" +
        "Choose ( r, p, s, q ):\n" +
        "Computer chose: SCISSORS\n" +
        "  Player chose: ROCK\n" +
        "Player scores!\n" +
        "Player wins! 3 : 2\n", out);
    }

    /** tests the computer's choices */
    private static void testMakeChoice() {
    // test that the computer is picking reproducible values
        RPS.reset(0);
        assert RPS.makeChoice() == RPS.ROCK;
        assert RPS.makeChoice() == RPS.PAPER;
        assert RPS.makeChoice() == RPS.PAPER;
        assert RPS.makeChoice() == RPS.SCISSORS;
        assert RPS.makeChoice() == RPS.SCISSORS;
        assert RPS.makeChoice() == RPS.SCISSORS;
        assert RPS.makeChoice() == RPS.SCISSORS;
        assert RPS.makeChoice() == RPS.ROCK;
        assert RPS.makeChoice() == RPS.ROCK;
        assert RPS.makeChoice() == RPS.SCISSORS;

        RPS.reset(1181);
        assert RPS.makeChoice() == RPS.SCISSORS;
        assert RPS.makeChoice() == RPS.ROCK;
        assert RPS.makeChoice() == RPS.ROCK;
        assert RPS.makeChoice() == RPS.SCISSORS;
        assert RPS.makeChoice() == RPS.SCISSORS;

        RPS.reset(201720);
        assert RPS.makeChoice() == RPS.ROCK;
        assert RPS.makeChoice() == RPS.PAPER;
        assert RPS.makeChoice() == RPS.SCISSORS;
        assert RPS.makeChoice() == RPS.SCISSORS;
        assert RPS.makeChoice() == RPS.SCISSORS;
    }

    private static void assertOutput(String expected, OutputStream out) {
        expected = expected.replace("\r", "");
        String actual = out.toString().replace("\r", "");
        assert expected.equals(actual) :
        expected.length() + " " + actual.length() + "\n" +
        expected + actual;
    }

    private static OutputStream resetSystemOut() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        return baos;
    }
}
