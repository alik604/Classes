import java.awt.*;
import java.awt.geom.*;

/**
 * The Class ShipImpl.
 */
public class ShipImpl implements Ship {

	private final static Color FILL = Color.GREEN;
	private final static Color BORDER = Color.BLACK;
	private final static int HEIGHT = 20;
	private final static int WIDTH = HEIGHT;
	private final Polygon shape;
	private Direction d;
	private Rectangle2D movementBounds;

	/**
	 * Instantiates a new ship implementation.
	 *
	 * @param x
	 *            the Xpos
	 * @param y
	 *            the Ypos
	 */
	public ShipImpl(int x, int y) {
		shape = new Polygon(new int[] { 0, 0, WIDTH },
		// top left, bottom left, front middle
				new int[] { 0, HEIGHT, HEIGHT / 2 }, 3);
		shape.translate(x, y);
		d = Direction.NONE;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Ship#setDirection(Ship.Direction)
	 */
	public void setDirection(Direction d) {
		this.d = d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Ship#setMovementBounds(java.awt.geom.Rectangle2D)
	 */
	public void setMovementBounds(Rectangle2D movementBounds) {
		this.movementBounds = movementBounds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Sprite#move()
	 */
	public void move() {
		shape.translate(d.dx, d.dy);
		// if (movementBounds != null) {
		if (shape.xpoints[0] <= movementBounds.getMinX()
				|| shape.ypoints[0] <= movementBounds.getMinY()
				|| shape.ypoints[1] >= movementBounds.getMaxY()
				|| shape.xpoints[2] >= movementBounds.getMaxX()) {
			shape.translate(-d.dx, -d.dy);
			// }

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Sprite#draw(java.awt.Graphics2D)
	 */
	public void draw(Graphics2D g, int hits) {
		if (hits == 0) {
			g.setPaint(FILL);
			g.fill(shape);

			// no clue what i did below, but it matches the pic, and is 100% my
			// work
			g.setPaint(BORDER);
			Stroke s = g.getStroke();
			g.fill(s.createStrokedShape(shape));
		} else {
			g.setPaint(BORDER);
			Stroke s = g.getStroke();
			g.fill(s.createStrokedShape(shape));
		}
	}

	public void draw(Graphics2D g) {
		g.setPaint(BORDER);
		Stroke s = g.getStroke();
		g.fill(s.createStrokedShape(shape));
	}

	/**
	 * Gets the nose.
	 *
	 * @return the nose
	 */
	public Point getNose() {
		return new Point(shape.xpoints[2], shape.ypoints[2]);// or ypoints[2]
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
	// reference used
	// https://stackoverflow.com/questions/22062600/how-to-find-the-area-of-intersect-of-recangle2d-in-java
	// https://stackoverflow.com/questions/15690846/java-collision-detection-between-two-shape-objects
	public boolean intersects(Sprite other) {
		Area ship = new Area(shape);
		Area strd = new Area(other.getShape());
		ship.intersect(strd);

		return !ship.isEmpty();
	}
}
