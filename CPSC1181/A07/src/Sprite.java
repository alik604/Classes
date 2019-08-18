import java.awt.*;

/**
 * The Interface Sprite.
 */
public interface Sprite {
	
	/**
	 * Draw.
	 *
	 * @param g the g
	 */
	public void draw(Graphics2D g);
	
	/**
	 * Move.
	 */
	public void move();
	
	/**
	 * Gets the shape.
	 *
	 * @return the shape
	 */
	public Shape getShape(); // used by intersects
	
	/**
	 * Intersects.
	 *
	 * @param other the other
	 * @return true, if successful
	 */
	public boolean intersects(Sprite other);
}
