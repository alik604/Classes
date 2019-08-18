package A02;

import java.awt.geom.Point2D;


/*
 * using tester2
 *
 */
/**
 * <h1>a02: Classes and Objects: Robot</h1>.
 *
 * @author Khizr Pardhan | 100282129
 * @version 1.0
 * @see https
 *      ://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db
 *      =50361&grpid=0&isprv=0&bp=0&ou=88736
 * @since 2017-05-17 10:00pm
 */

public class Robot {

	private final String model;
	private final static double MAXRAD = Math.PI * 2;
	private final static long SERIAL_LHS = 10 * 1000 * 1000 * 1000L;
	private final static int WARRANTY = 100 * 1000;

	private static int serialNumber = 100;

	private Point2D.Double point = new Point2D.Double(0, 0);

	private String name = "";
	private final long SerialNumber;
	private double speed = 0;
	private double direction = 0;
	private double turnrate = 0;
	private double obo = 0;
	private double posX = 0;
	private double posY = 0;

	/**
	 * constructor for robot.
	 *
	 * @param aModel
	 *            String of model name
	 * @param aName
	 *            String of name
	 * @param aTurnRate
	 *            value of rate of turning
	 */
	public Robot(String aModel, String aName, double aTurnRate) {
		model = aModel;
		name = aName;
		turnrate = aTurnRate;

		// serialNumber++;

		long mylong = model.hashCode() * 10000000000l;

		if (mylong < 0)
			mylong *= -1;

		mylong -= mylong % 10000000000l;

		SerialNumber = mylong + ++serialNumber;
	}

	/**
	 * Gets the model of the robot.
	 *
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Gets the serial of the robot.
	 *
	 * @return the serial
	 */
	public long getSerial() {
		return SerialNumber;
	}

	/**
	 * Gets the name of the robot.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the robot.
	 *
	 * @param aName
	 *            the new name
	 */
	public void setName(String aName) {
		name = aName;
	}

	/**
	 * Gets the direction of the robot.
	 *
	 * @return the direction
	 */
	public double getDirection() {
		return direction;
	}

	/**
	 * Gets the turn rate of the robot.
	 *
	 * @return the turn rate
	 */
	public double getTurnRate() {
		return turnrate;
	}

	/**
	 * Gets the speed.
	 *
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed of the robot.
	 *
	 * @param aSpeed
	 *            the new speed
	 */
	public void setSpeed(double aSpeed) {
		speed = aSpeed;
	}

	/**
	 * Turn right.
	 */
	public void turnRight() {
		if (direction - turnrate < 0)
			direction = MAXRAD - (direction + turnrate);
		else
			direction -= getTurnRate();
	}

	/*
	 * Dear marker,
	 * 
	 * which is "better" (direction + turnrate) or (getDirection() +
	 * getTurnRate())
	 * 
	 * what do you think of use of autoJdoc
	 */
	/**
	 * Turn the robot left.
	 */
	public void turnLeft() {
		if (getDirection() + getTurnRate() >= MAXRAD)
			direction = (getDirection() + getTurnRate()) - MAXRAD;
		else
			direction += getTurnRate();
	}

	/**
	 * Gets the position of the robot.
	 *
	 * @return the position
	 */
	public Point2D.Double getPosition() {
		return point;
	}

	/**
	 * Moves the robot.
	 */
	public void move() {
		// last point + (f(dir)*speed))
		// f being a function(sin|cos) upon dir
		posX = point.getX() + speed * Math.cos(direction);
		posY = point.getY() + speed * Math.sin(direction);

		point.setLocation(posX, posY);// im not simplifying this

		obo += speed;
	}

	/**
	 * Gets the odometer of the robot.
	 *
	 * @return the odometer
	 */
	public double getOdometer() {
		return obo;
	}

	/**
	 * Gets the warranty left.
	 *
	 * @return the warranty left
	 */
	public double getWarrantyLeft() {
		return WARRANTY - obo;
	}

	/**
	 * Checks if is under warranty.
	 *
	 * @return true, if is under warranty
	 */
	public boolean isUnderWarranty() {
		if (getWarrantyLeft() > 0)
			return true;
		return false;
	}
}
