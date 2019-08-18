import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

/**
 * The Class GameComponent.
 */
public class GameComponent extends JComponent {
	/*
	 * to speed up start if listStrd.removeIf(a -> !a.isVisible()) has never
	 * been true, game is still starting
	 */
	private final int FRAME_RATE = 1000 / 60;
	private final int ASTRD_RATE = 1000 / 8;
	private final int POWER_UP_RATE = 7500;

	private int hitCount;
	private int score;
	final ShipImpl ship;
	ShipKeyListener myShipKeyListener;

	Timer timerF;
	Timer timerA;
	Timer timerP;
	Timer[] timerList = new Timer[3];

	ArrayList<Asteroid> listStrd;
	ArrayList<Cannon> listCan;
	ArrayList<PowerUp> listPowerUp;

	/**
	 * Instantiates a new game component.
	 */
	public GameComponent() {
		setFocusable(true);

		myShipKeyListener = new ShipKeyListener();
		addKeyListener(myShipKeyListener);
		// IDK how to remove anon obj, so i gave it a name

		ship = new ShipImpl(10, GameFrame.HEIGHT / 3);

		AsteroidFactory.getInstance().setStartBounds(900, 5, 650);

		hitCount = 0;
		score = 0;

		// **** timer and date structure setup ****//
		timerF = new Timer(FRAME_RATE, u -> {
			update();
		});

		timerA = new Timer(ASTRD_RATE, u -> {
			makeAsteroid();
			new GameFrame().updateText();
			// ill be sloppy and add this here. i think this might be a HUGE
			// Resorse leak. (i don't what it to run too often). should be in
			// update or asteroid collision
			// but its an anon obj to JVM garbage collection should help me out
			});
		timerP = new Timer(POWER_UP_RATE, p -> {
			makePowerUp();
		});

		timerList[0] = timerF;
		timerList[1] = timerA;
		timerList[2] = timerP;

		listStrd = new ArrayList<Asteroid>();
		listCan = new ArrayList<Cannon>();
		listPowerUp = new ArrayList<PowerUp>();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		paint((Graphics2D) g);
	}

	/**
	 * Paint.
	 *
	 * @param g
	 *            the Graphics2D OBJ
	 */
	private void paint(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		ship.draw(g, hitCount);

		for (Asteroid a : listStrd) {
			a.draw(g);
		}
		for (Cannon c : listCan) {
			c.draw(g);
		}
		for (PowerUp p : listPowerUp) {
			p.draw(g);
		}
		int max = 400;
		int func = max - (80 * hitCount);

		// *** health bar ***/
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, max, 12);
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, func, 10);

		// *** score ***/

	}

	/**
	 * Start game
	 */
	public void start() {
		ship.setMovementBounds(new Rectangle2D.Double(0, 0, getWidth(),
				getHeight()));
		// set to appropriate width and height (assuming typo in instructions)
		for (Timer t : timerList) {
			t.start();
		}

	}

	/**
	 * Make power up.
	 */
	private void makePowerUp() {// not sure if void or return astrd
		listPowerUp.add(PowerUpFactory.getInstance().makePowerUp());
	}

	/**
	 * Move power up.
	 */
	private void movePowerUp() {
		for (PowerUp p : listPowerUp) {
			p.move();
		}
	}

	/**
	 * Make cannon shot.
	 */
	private void makeCannonShot() {
		listCan.add(CannonFactory.getInstance().makeCannon(ship.getNose().x,
				ship.getNose().y));
	}

	/**
	 * Move cannon shot.
	 */
	private void moveCannonShot() {
		for (Cannon a : listCan) {
			a.move();
		}
	}

	/**
	 * Make asteroid.
	 */
	private void makeAsteroid() {// not sure if void or return astrd
		listStrd.add(AsteroidFactory.getInstance().makeAsteroid());
	}

	/**
	 * Move asteroids.
	 */
	private void moveAsteroids() {
		for (Asteroid a : listStrd) {
			a.move();
		}
	}

	/**
	 * Check collisions.
	 *
	 * @return true, there is a collision
	 */
	public boolean checkCollisions() {
		if (listStrd.removeIf(a -> a.intersects(ship))) {
			// not sure if against best practice
			hitCount++;
			if (hitCount == 5) {
				gameOver();
			}
			return true;
		}
		return false;
	}

	/**
	 * Check collision can.
	 */
	public void checkCollisionCan() {
		try {
			for (Asteroid a : listStrd) {
				for (Cannon c : listCan) {
					if (c.intersects(a)) {
						listStrd.remove(a);
						listCan.remove(c);
						score += 10;
					}
				}
			}
		} catch (Exception e) {// ConcurrentModificationException.
			// i see no point is being specific, when i don't handle anything
		}
	}

	/**
	 * Check collisions power up.
	 *
	 * @return true, if successful
	 */
	public boolean checkCollisionsPowerUp() {
		if (listPowerUp.removeIf(a -> a.intersects(ship))) {
			// not sure if against best practice
			if (hitCount > 0) {
				hitCount--;
			}
			return true;
		}
		return false;

	}

	/**
	 * Collect garbage. (cannonShot,asteroid and powerUps
	 */
	public void collectGarbage() {
		listCan.removeIf(c -> !c.isVisible());
		listStrd.removeIf(a -> !a.isVisible());
		listPowerUp.removeIf(p -> !p.isVisible());
	}

	/**
	 * Game over.
	 */
	public void gameOver() {
		timerA.stop();
		timerF.stop();
		timerP.stop();
		removeKeyListener(myShipKeyListener);
	}

	/**
	 * Update.
	 */
	private void update() {
		requestFocusInWindow();// first
		ship.move();// Second
		moveAsteroids();// 3rd
		moveCannonShot();
		movePowerUp();
		/* things to check */
		checkCollisions();// w/ ship strd
		checkCollisionCan();// w/ cannon shots strd
		checkCollisionsPowerUp(); // w/ blue orbs
		collectGarbage();
		repaint();// last
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * The listener interface for receiving shipKey events. The class that is
	 * interested in processing a shipKey event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addShipKeyListener<code> method. When
	 * the shipKey event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see ShipKeyEvent
	 */
	// could likely put into new file, but id rather just minimize it
	class ShipKeyListener extends KeyAdapter {
		boolean up, down, left, right, shoot;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
		 */
		public void keyPressed(KeyEvent e) {
			fireHand(e.getKeyCode(), true);
			// stack overflow thought me this
			// due to rocky movement i tried this; its works better. but added
			// overhead of extra method
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
		 */
		public void keyReleased(KeyEvent e) {
			fireHand(e.getKeyCode(), false);
		}

		/**
		 * Fire handler.
		 *
		 * @param keyCode
		 *            the key code
		 * @param action
		 *            the action
		 */
		public void fireHand(int keyCode, boolean action) {
			// done this in a way to allow left or right hand only game play.
			// wanted to add 5 to setDir to down, but could not. IDk how to
			// check for
			// multi keyevents at once
			switch (keyCode) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
			case KeyEvent.VK_KP_UP:
				up = action;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
			case KeyEvent.VK_KP_RIGHT:
				right = action;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
			case KeyEvent.VK_KP_LEFT:
				left = action;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
			case KeyEvent.VK_KP_DOWN:
				down = action;
				break;
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_ENTER:
				shoot = action;
				break;
			}
			if (up) {
				ship.setDirection(Ship.Direction.UP);
			}
			if (down) {
				ship.setDirection(Ship.Direction.DOWN);
			}
			if (left) {
				ship.setDirection(Ship.Direction.LEFT);
			}
			if (right) {
				ship.setDirection(Ship.Direction.RIGHT);
			}
			if (up && right) {
				ship.setDirection(Ship.Direction.UPRIGHT);
			}
			if (up && left) {
				ship.setDirection(Ship.Direction.UPLEFT);
			}
			if (down && right) {
				ship.setDirection(Ship.Direction.DOWNRIGHT);
			}
			if (down && left) {
				ship.setDirection(Ship.Direction.DOWNLEFT);
			}
			if (shoot) {
				makeCannonShot();// rapid fire, like in arcade games of same era
			}
			if (!up && !down && !right && !left) {
				ship.setDirection(Ship.Direction.NONE);
			}
		}
	}
}// GameComponent class

