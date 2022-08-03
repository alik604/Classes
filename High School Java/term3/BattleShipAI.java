package term3.term3;

//Amory KC Wong
//Carson Graham Secondary
//2016-04-23
//Exercise_10_1
//This is the only file that should be modified
//Modify with whatever algorithm you wish to create
//Use arrays and the random number generate as a hint

import java.awt.Point;

public class BattleShipAI {

	int x;
	int y;

	BattleShipAI() {
		x = 0;
		y = 0;
	}

	// example of how to use the check routine
	Point pickEmpty(BattleShip game) {
		Point p = new Point(0, 0);
		for (int i = 0; i < BattleShip.boardSize; i++)
			for (int j = 0; j < BattleShip.boardSize; j++)
				if (game.checkHitOrMiss(i, j) == 0)
					p = new Point(i, j);
		return p;
	}

	// this algorithm is brute force, it just goes and checks each grid from
	// left to right and top to bottom
	void fire(BattleShip game) {
		Point spot = new Point(x, y);

		// spot.x = (int) (Math.random() * BattleShip.boardSize);
		// spot.y = (int) (Math.random() * BattleShip.boardSize);

		if (++x == BattleShip.boardSize) {

			x = 0;
			y++;
		}

		game.fireShot(spot.x, spot.y);
	}

}