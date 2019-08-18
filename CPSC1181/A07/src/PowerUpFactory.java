import java.util.Random;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;

/**
 * A factory for creating PowerUp objects.
 */
public class PowerUpFactory {

	private final static PowerUpFactory instance = new PowerUpFactory();

	/**
	 * Instantiates a new power up factory.
	 */
	private PowerUpFactory() {
	}

	/**
	 * Gets the single instance of PowerUpFactory.
	 *
	 * @return single instance of PowerUpFactory
	 */
	public static PowerUpFactory getInstance() {
		return instance;
	}

	/**
	 * Make power up.
	 *
	 * @return the power up
	 */
	public PowerUp makePowerUp() {

		return new PowerUpImpl(910, random(20, 680), 20, 10);
	}

	/**
	 * Random.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the int
	 */
	private static int random(int min, int max) {
		Random rand = java.util.concurrent.ThreadLocalRandom.current();
		return min + (int) (rand.nextDouble() * (max - min));
	}

	/**
	 * The Class PowerUpImpl.
	 */
	private static class PowerUpImpl implements PowerUp {
		private final static Color COLOR = Color.blue;
		private final Ellipse2D.Double shape;
		private final int speed;
		private boolean isUp = new Random().nextBoolean();

		/**
		 * Instantiates a new power up impl.
		 *
		 * @param x the x
		 * @param y the y
		 * @param side the side lenght
		 * @param speed the speed
		 */
		private PowerUpImpl(int x, int y, int side, int speed) {
			shape = new Ellipse2D.Double(x, y, side, side);
			this.speed = speed;
		}

		/* (non-Javadoc)
		 * @see Sprite#move()
		 */
		public void move() {
			// there gravitational attraction to top and bottom
			shape.x -= speed;
			if (isUp) {
				shape.y -= 10;
			} else {
				shape.y += 10;
			}
			if (shape.y < 5) {
				isUp = !isUp;
			}
			if (shape.y > 650) {
				isUp = !isUp;
			}
		}

		/* (non-Javadoc)
		 * @see PowerUp#isVisible()
		 */
		public boolean isVisible() {
			return (shape.x <= -10 ? false : true);
		}

		/* (non-Javadoc)
		 * @see Sprite#draw(java.awt.Graphics2D)
		 */
		public void draw(Graphics2D g) {
			g.setColor(COLOR);
			g.fill(shape);
		}

		/* (non-Javadoc)
		 * @see Sprite#getShape()
		 */
		public Shape getShape() {
			return shape;
		}

		/* (non-Javadoc)
		 * @see Sprite#intersects(Sprite)
		 */
		public boolean intersects(Sprite other) {
			Area s = new Area(other.getShape());
			Area powerUpArea = new Area(shape);
			powerUpArea.intersect(s);
			return !powerUpArea.isEmpty();
		}
	}
}