import java.util.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * A factory for creating Asteroid objects.
 */
public class AsteroidFactory {

	private final static AsteroidFactory instance = new AsteroidFactory();

	private static Rectangle startBounds;

	/**
	 * Instantiates a new asteroid factory.
	 */
	private AsteroidFactory() {
	}

	/**
	 * Gets the single instance of AsteroidFactory.
	 *
	 * @return single instance of AsteroidFactory
	 */
	public static AsteroidFactory getInstance() {
		return instance;
	}

	/**
	 * Sets the start bounds.
	 *
	 * @param x
	 *            the x
	 * @param minY
	 *            the min Y
	 * @param maxY
	 *            the max Y
	 */
	public void setStartBounds(int x, int minY, int maxY) {
		startBounds = new Rectangle(x, minY, x, maxY);
	}

	/**
	 * Make asteroid.
	 *
	 * @return the asteroid
	 */
	public Asteroid makeAsteroid() {
		return new AsteroidImpl(startBounds.x, random(startBounds.y,
				startBounds.height), random(8, 35), random(8, 35),
				random(3, 7), random(1, 20));
	}

	/**
	 * Random.
	 *
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return the random int
	 */
	private static int random(int min, int max) {
		Random rand = java.util.concurrent.ThreadLocalRandom.current();
		return min + (int) (rand.nextDouble() * (max - min));
	}

	/**
	 * The Class AsteroidImpl.
	 */
	private static class AsteroidImpl implements Asteroid {
		private Color color = Color.DARK_GRAY;
		private final Ellipse2D.Double shape;
		private final double ARK_SPEED;
		private double speed;

		/**
		 * Instantiates a new asteroid impl.
		 *
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param w
		 *            the w
		 * @param h
		 *            the h
		 * @param speed
		 *            the speed
		 * @param reddieChance
		 *            the chance of red asteroid
		 */
		// not sure if i should have overloaded, but the game works
		private AsteroidImpl(int x, int y, int w, int h, double speed,
				int reddieChance) {
			if (reddieChance == 7) {
				color = Color.RED;
				speed += 5;
			}
			shape = new Ellipse2D.Double(x, y, w, h);
			this.ARK_SPEED = this.speed = speed;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Sprite#move()
		 */
		public void move() {
			if (speed <= ARK_SPEED + 2) {
				speed += 0.005;
			}
			shape.x -= speed;

			if (shape.y > startBounds.getCenterY()) {
				shape.y += 0.098;
			} else {
				shape.y -= 0.098;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Asteroid#isVisible()
		 */
		public boolean isVisible() {
			return (shape.x <= -25 ? false : true);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Sprite#draw(java.awt.Graphics2D)
		 */
		public void draw(Graphics2D g) {
			g.setColor(color);
			g.fill(shape);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Sprite#getShape()
		 */
		public Shape getShape() {
			return shape;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Sprite#intersects(Sprite)
		 */
		public boolean intersects(Sprite other) {
			return other.intersects(this);
			// dear marker, why "this" as param?
		}
	}// end of AsteroidImpl
}// end of AsteroidFactory