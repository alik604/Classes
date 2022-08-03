package term3.term3;

//Amory KC Wong
//Carson Graham Secondary
//2016-04-23
//Exercise_10_1

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BattleShip {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final boolean DEBUG = false;
    public static final boolean FULL_TEST = true;
    public static final Scanner input = new Scanner(System.in); // set up to get input
    // from console
    private static Random rand  = new Random();
    static int boardSize = 10;
    int[][] board; // y-coord comes before x-coord with arrays
    // an unhit ship will be the ship index+2
    // 0 - unfired, 1 - miss
    // a hit ship will be the negative ship index+2
    int[] shipSizes;
    int numShots;
    ArrayList<Ship> ships;
    boolean gameOver;
    BattleShip() {
        this.board = new int[boardSize][boardSize];
        this.shipSizes = new int[]{2, 3, 3, 4, 5};
        this.clearBoard();
    }

    public static void main(String[] args) {
        if (FULL_TEST) {
            int total = 0;
            int maxS = 0;
            int minS = 100;
            int maxSeed = 10000;
            long startTime = System.currentTimeMillis();

            for (int seed = 1; seed <= maxSeed; seed++) {
                rand = new Random(seed);
                BattleShip game = new BattleShip();
                BattleShipAI ai = new BattleShipAI();
                while (!game.gameOver) {
                    ai.fire(game); // you need to call game.fireShot(x,y);
                    if (DEBUG) game.printBoard();
                }
                // game.printBoard();
                System.out.println(ANSI_GREEN + "Number of shots " + game.numShots);
                total += game.numShots;
                if (maxS < game.numShots) maxS = game.numShots;
                if (minS > game.numShots) minS = game.numShots;
            }

            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Average number of shots = " + (double) (total) / maxSeed);
            System.out.println("Best number of shots = " + minS);
            System.out.println("Worst number of shots = " + maxS);
            System.out.println("Average time (ms) per game = " + (double) (elapsedTime) / maxSeed);
        } else {
            System.out.print("Enter random seed: ");
            String str = input.next(); // get the input as a string
            int seed = Integer.parseInt(str);
            rand = new Random(seed);
            long startTime = System.currentTimeMillis();
            BattleShip game = new BattleShip();
            BattleShipAI ai = new BattleShipAI();
            while (!game.gameOver) {
                ai.fire(game); // you need to call game.fireShot(x,y);
                // game.printBoard(); // uncomment this if you need to see your
                // AI step by step
            }
            game.printBoard();
            System.out.println(ANSI_GREEN + "Number of shots " + game.numShots);
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Time (ms) = " + elapsedTime);
        }
    }

    // clear the board and randomly place the ships
    void clearBoard() {
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                this.board[i][j] = 0;
        this.ships = new ArrayList<>();
        for (int i = 0; i < shipSizes.length; i++) {
            boolean added = false;
            int x;
            int y;
            while (!added) {
                boolean horizontal = (rand.nextInt(2) == 0);
                if (horizontal) {
                    x = rand.nextInt(boardSize - shipSizes[i]);
                    y = rand.nextInt(boardSize);
                } else {
                    x = rand.nextInt(boardSize);
                    y = rand.nextInt(boardSize - shipSizes[i]);
                }
                added = this.placeShip(shipSizes[i], x, y, horizontal);
            }
        }
        this.numShots = 0;
        this.gameOver = false;
    }

    // if DEBUG is true, it will print the locations of the ships
    // otherwise, it will print hit or misses
    void printBoard() {
        System.out.print(ANSI_RED + " ");
        for (int i = 0; i < boardSize; i++)
            // print grid heading
            System.out.print(i);
        System.out.println();
        for (int i = 0; i < boardSize; i++) {
            System.out.print(ANSI_RED);
            System.out.print((char) (i + 'A')); // print grid heading
            for (int j = 0; j < boardSize; j++) {
                if (this.board[i][j] == 0) {
                    System.out.print(" "); // print blank
                } else if (this.board[i][j] == 1) {
                    System.out.print(ANSI_BLACK + "X"); // print miss
                } else if (this.board[i][j] > 1) {
                    if (DEBUG) System.out.print(ANSI_BLUE + "S"); // print ship
                        // location
                    else System.out.print(" "); // print blank
                } else {
                    System.out.print(ANSI_RED + "O"); // print hit
                }
            }
            System.out.println();
        }
    }

    // returns true if it is okay to place the ship
    // returns false if the ship lies on top of another ship
    boolean placeShip(int size, int x, int y, boolean horizontal) {
        int shipNum = this.ships.size() + 2;
        if (horizontal) {
            for (int s = 0; s < size; s++)
                if (this.board[y][x + s] != 0) return false;
            for (int s = 0; s < size; s++)
                this.board[y][x + s] = shipNum;
        } else {
            for (int s = 0; s < size; s++)
                if (this.board[y + s][x] != 0) return false;
            for (int s = 0; s < size; s++)
                this.board[y + s][x] = shipNum;
        }
        Ship ship = new Ship(size, x, y, horizontal);
        this.ships.add(ship);
        return true;
    }

    // returns 0 if bad shot
    // returns 1 if missed shot
    // returns -1 if hit an unsunk ship
    // returns -2 if sunk
    // returns -100 if game over
    // all shots even if bad or already shot spot will count as a shot (use
    // checkHitOrMiss to avoid this)
    int fireShot(int x, int y) {
        int check = checkHitOrMiss(x, y);
        this.numShots++;
        if (check == 2) return 0; // bad coordinates
        if (check == 1) return 1; // already missed
        if (this.board[y][x] == 0) {
            this.board[y][x] = 1;
            return 1; // firing on an empty spot
        }
        int shipNum = Math.abs(this.board[y][x]) - 2;
        Ship ship = this.ships.get(shipNum);
        if (this.board[y][x] < 0) {
            if (ship.sunk == 0) return -2; // firing on a sunk ship will just count as a miss
            return -1;
        }
        this.board[y][x] = -this.board[y][x];
        if (--ship.sunk == 0) { // ship is sunk, check others
            for (Ship s : this.ships)
                if (s.sunk != 0) return -2; // ship sunk
            this.gameOver = true;
            return -100; // game complete
        }
        return -1; // ship hit, but not sunk
    }

    // returns 2 if bad shot
    // returns 1 if missed shot or already sunk ship
    // returns 0 if empty spot
    // returns -1 if hit
    // returns -2 if sunk ship
    int checkHitOrMiss(int x, int y) {
        if (x < 0 || y < 0 || x >= boardSize || y >= boardSize) return 2; // eliminate bad coordinates
        if (this.board[y][x] == 0) return 0; // empty spot
        if (this.board[y][x] == 1) return 1; // missed spot
        int shipNum = Math.abs(this.board[y][x]) - 2;
        Ship ship = this.ships.get(shipNum);
        if (this.board[y][x] < 0) {
            if (ship.sunk == 0) return -2; // sunk ship
            return -1; // unsunk ship
        }
        return 0; // empty spot
    }

    class Ship {
        int size; // valid sizes are 2,3,4,5
        int x;
        int y;
        boolean horizontal;
        int sunk; // when size is 0, it is sunk

        Ship(int size, int x, int y, boolean horizontal) {
            this.size = size;
            this.x = x;
            this.y = y;
            this.horizontal = horizontal;
            this.sunk = size;
        }
    }

}