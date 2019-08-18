import java.awt.*;
import java.awt.geom.*;

/**
 * A factory for creating Cannon objects.
 */
public class CannonFactory {
	private final static CannonFactory instance = new CannonFactory();

	/**
	 * Instantiates a new cannon factory.
	 */
	private CannonFactory() {
	}

	/**
	 * Gets the single instance of CannonFactory.
	 *
	 * @return single instance of CannonFactory
	 */
	public static CannonFactory getInstance() {
		return instance;
	}

	/**
	 * Make cannon.
	 *
	 * @param x
	 *            the xPos
	 * @param y
	 *            the yPos
	 * @return the cannonImpl
	 */
	public Cannon makeCannon(int x, int y) {

		return new CannonImpl(x, y, 8, 8, 10);
	}

	/**
	 * The Class CannonImpl.
	 */
	private static class CannonImpl implements Cannon {
		private static int instanceNumber = 1;
		private  Color col;

		private final Ellipse2D.Double shape;
		private final int speed;

		/**
		 * Instantiates a new cannon impl.
		 *
		 * @param x
		 *            the Xpos
		 * @param y
		 *            the Ypos
		 * @param w
		 *            the width
		 * @param h
		 *            the height
		 * @param speed
		 *            the speed
		 */
		private CannonImpl(int x, int y, int w, int h, int speed) {
			shape = new Ellipse2D.Double(x - w, y - h / 2, w, h);
			this.speed = speed;
			instanceNumber++;
			if (instanceNumber % 2 == 0) {
				col = Color.RED;
			} else {
				col = Color.MAGENTA;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Sprite#move()
		 */
		public void move() {
			// there there gravitational attraction at top and bottom
			shape.x += speed;
			if (shape.y > Frame.HEIGHT / 2) {
				shape.y += 0.098;
			} else {
				shape.y -= 0.098;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Cannon#isVisible()
		 */
		public boolean isVisible() {
			return (shape.x >= 905 ? false : true);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Sprite#draw(java.awt.Graphics2D)
		 */
		public void draw(Graphics2D g) {
			g.setColor(col);
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
			Area strd = new Area(other.getShape());
			Area canArea = new Area(shape);
			canArea.intersect(strd);
			return !canArea.isEmpty();
		}
	}
}