// use RobotTester1 or RobotTester2, NOT BOTH
// indicate in your Robot's top comments which one you used
package A02;

import java.awt.geom.Point2D;

public class RobotTester {

	private final static int WARRANTY = 100 * 1000;

	static {
		boolean assertsEnabled = false;
		assert assertsEnabled = true; // Intentional side effect!!!
		if (!assertsEnabled) {
			throw new RuntimeException("Asserts must be enabled!!! java -ea");
		}
	}

	private final static Point2D.Double ORIGIN = new Point2D.Double(0, 0);

	public static void main(String[] args) {
		testConstructRobot();
		testChangeName();
		testSetSpeed();
		testTurn();
		testMove_d8s(1); // typical
		testMove_d8s(2); // boundary
		testMove_d8s(0); // boundary, special
		testMove_d8s(0.5); // typical
		testMove_d8s(Math.sqrt(2)); // typical
		testMove_path(); // typical
		testOdometerWarranty(1); // typical (&boundary for warranty)
		testOdometerWarranty(2); // boundary (&boundary for warranty)
		testOdometerWarranty(0.5); // typical (&boundary for warranty)
		testOdometerWarranty(Math.sqrt(2)); // typical
		System.out.println("PASS");
	}

	private static void testConstructRobot() {
		Robot r;

		r = new Robot("Model-A", "Alice", 0);
		assert "Model-A".equals(r.getModel()) : "model";
		assert 4413158120000000101L == r.getSerial() : "serial";
		assert "Alice".equals(r.getName()) : "name";
		assert 0 == r.getDirection() : "direction";
		assert 0 == r.getTurnRate() : "turn rate";
		assert 0 == r.getSpeed() : "speed";
		assert ORIGIN.equals(r.getPosition()) : "position";
		assert 0 == r.getOdometer() : "odometer";
		assert WARRANTY == r.getWarrantyLeft() : "warantee";
		assert r.isUnderWarranty() : "under warranty";

		r = new Robot("Model-B", "Bob", Math.PI);
		assert "Model-B".equals(r.getModel()) : "model";
		assert 4413158130000000102L == r.getSerial() : "serial";
		assert "Bob".equals(r.getName()) : "name";
		assert 0 == r.getDirection() : "direction";
		assert Math.PI == r.getTurnRate() : "radius";
		assert 0 == r.getSpeed() : "speed";
		assert ORIGIN.equals(r.getPosition()) : "position";
		assert 0 == r.getOdometer() : "odometer";
		assert WARRANTY == r.getWarrantyLeft() : "warantee";
		assert r.isUnderWarranty() : "under warranty";
	}

	private static void testChangeName() {
		Robot r = new Robot("", "", 0);

		r.setName(null); // special
		assert null == r.getName();

		r.setName(""); // special
		assert "".equals(r.getName());

		r.setName("Chloe"); // typical
		assert "Chloe".equals(r.getName());
	}

	private static void testSetSpeed() {
		Robot r = new Robot("", "", 0);

		r.setSpeed(0.0); // boundary
		assert 0.0 == r.getSpeed();

		r.setSpeed(2.0); // boundary
		assert 2.0 == r.getSpeed();

		r.setSpeed(1.5); // typical
		assert 1.5 == r.getSpeed();
	}

	private static void testTurn() {
		Robot r;

		// typical, 8 directions
		r = new Robot("", "", Math.PI * 2 / 8);
		assert 0 == r.getDirection();
		assert Math.PI / 4 == r.getTurnRate();
		for (double d = 0; d < Math.PI * 2; d += Math.PI / 4) {
			assert r.getDirection() == d : d;
			r.turnLeft();
		}
		assert 0 == r.getDirection() : r.getDirection(); // boundary

		r.turnRight(); // boundary
		assert Math.PI * 2 - Math.PI / 4 == r.getDirection() : r.getDirection();
		for (double d = r.getDirection(); d > 0; d -= Math.PI / 4) {
			assert d == r.getDirection();
			r.turnRight();
		}
		assert 0 == r.getDirection(); // boundary

		// special, cant turn
		r = new Robot("", "", 0);
		r.turnLeft();
		assert 0 == r.getDirection();
		r.turnRight();
		assert 0 == r.getDirection();

		// boundary, full rotation
		r = new Robot("", "", Math.PI * 2);
		r.turnLeft();
		assert 0 == r.getDirection();
		r.turnRight();
		assert 0 == r.getDirection();

		// typical, 1 degree
		r = new Robot("", "", Math.PI * 2 / 360);
		r.turnLeft(); // trypical
		assert Math.PI * 2 / 360 == r.getDirection();
		r.turnRight(); // boundary
		assert 0 == r.getDirection();
		r.turnRight(); // boundary
		assert Math.PI * 2 - Math.PI * 2 / 360 == r.getDirection();
		r.turnLeft(); // boundary
		assert 0 == r.getDirection();
	}

	/** Tests moving the robot one unit in each of 8 directions */
	private static void testMove_d8s(double speed) {
		testMove(Math.PI * 2 / 8, speed, 0, speed, 0);
		testMove(Math.PI * 2 / 8, speed, 2, 0, speed);
		testMove(Math.PI * 2 / 8, speed, 4, -speed, 0);
		testMove(Math.PI * 2 / 8, speed, 6, 0, -speed);
		testMove(Math.PI * 2 / 8, speed, 8, speed, 0);
		final double hyp = (float) Math.sqrt(2 * Math.pow(speed, 2)) / 2;
		testMove(Math.PI * 2 / 8, speed, 1, hyp, hyp);
		testMove(Math.PI * 2 / 8, speed, 3, -hyp, hyp);
		testMove(Math.PI * 2 / 8, speed, 5, -hyp, -hyp);
		testMove(Math.PI * 2 / 8, speed, 7, hyp, -hyp);
	}

	public static void testMove(double turnRate, double speed, int turns,
			double x, double y) {

		Robot r = new Robot("", "", turnRate);
		r.setSpeed(speed);
		for (int i = 0; i < turns; i++) {
			r.turnLeft();
		}
		r.move();

		assert speed == r.getOdometer();

		final Point2D.Double expected = new Point2D.Double(x, y);
		final Point2D.Double actual = r.getPosition();
		assert equals(expected, actual) : expected + " " + actual;
		final double actualD = actual.distance(ORIGIN);
		assert equals(speed, actualD) : speed + " " + actualD;
	}

	/** Moves the robot through a predetermined path */
	private static void testMove_path() {
		Robot r = new Robot("", "", Math.PI * 2 / 4); // 4 directions
		r.setSpeed(1);
		r.move();
		assert equals(new Point2D.Double(1, 0), r.getPosition());
		assert equals(1, r.getOdometer());
		r.turnLeft();
		r.move();
		assert equals(new Point2D.Double(1, 1), r.getPosition());
		assert equals(2, r.getOdometer());
		r.turnRight();
		r.move();
		assert equals(new Point2D.Double(2, 1), r.getPosition());
		assert equals(3, r.getOdometer());
		r.turnRight();
		r.move();
		r.move();
		assert equals(new Point2D.Double(2, -1), r.getPosition());
		assert equals(5, r.getOdometer());
		r.turnRight();
		r.move();
		r.move();
		r.move();
		assert equals(new Point2D.Double(-1, -1), r.getPosition());
		assert equals(8, r.getOdometer());
		r.turnRight();
		r.move();
		assert equals(new Point2D.Double(-1, 0), r.getPosition());
		assert equals(9, r.getOdometer());
		r.turnRight();
		r.move();
		assert equals(new Point2D.Double(0, 0), r.getPosition());
		assert equals(10, r.getOdometer());
		assert equals(0, r.getDirection());
	}

	public static void testOdometerWarranty(double speed) {
		Robot r = new Robot("", "", 0);
		r.setSpeed(speed);

		double expected;
		for (expected = 0; expected < WARRANTY; expected += speed) {
			assert expected == r.getOdometer() : expected + " "
					+ r.getOdometer();
			assert WARRANTY - expected == r.getWarrantyLeft();
			assert r.isUnderWarranty();
			r.move();
		}
		assert r.getOdometer() >= WARRANTY;
		assert r.getWarrantyLeft() <= 0;
		assert !r.isUnderWarranty();

		// still moves
		r.move();
		expected += speed;
		assert expected == r.getOdometer();
		assert r.getWarrantyLeft() <= 0;
		assert !r.isUnderWarranty();
	}

	private final static float EPSILON = 1.0E-6f;

	private static boolean equals(Point2D.Double a, Point2D.Double b) {
		return equals(a.x, b.x) && equals(a.y, b.y);
	}

	private static boolean equals(double a, double b) {
		return Math.abs(a - b) < EPSILON;
	}
}
