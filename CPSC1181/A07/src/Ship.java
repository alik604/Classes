import java.awt.geom.Rectangle2D;

public interface Ship extends Sprite {

	public enum Direction {
		NONE(0, 0), UP(0, -4), DOWN(0, 4), LEFT(-4, 0), RIGHT(4, 0), UPRIGHT(4,
				-4), UPLEFT(-4, -4), DOWNRIGHT(4, 4), DOWNLEFT(-4, 4);

		public final int dx;
		public final int dy;

		/**
		 * Instantiates a new direction.
		 *
		 * @param dx the dx
		 * @param dy the dy
		 */
		Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}
	};

	public void setDirection(Direction d);

	public void setMovementBounds(Rectangle2D bounds);
}
