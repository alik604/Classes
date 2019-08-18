/**
 * Created by Khizr ali pardhan on 2/29/2016.
 * inclined plane calculator 
 * formula used: mg sin * - mg cos * μ = fn
 * fn/m = a
 * basic rounding by user selected precision 
 * 
 */

package term2;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

public class InclinePlaneCalq {
	// unicode for mu ---> "\u00B5"
	public static int CalSigFigs(double x) {
		// unused method
		x = 12345;
		double ten = 10;
		// double log = Math.toRadians(ten);
		double n = Math.ceil(Math.log(x) / Math.log(10));

		while (x != Math.floor(x) || x % 10 == 0)
			if (x != Math.floor(x)) {
				x *= 10;
				n++;
			} else {
				x /= 10;
				n--;
			}
		return (int) n;
	}

	public static void main(String args[]) {
	
		System.out.println("Hello, \nWelcome to Ali's Incline Plane Calculator\n");
		Scanner scanner = new Scanner(System.in);

		System.out.println("please enter Theta:");
		double degrees = scanner.nextDouble();
		// int s = CalSigFigs(degrees);
		if (degrees >= 90)
			throw new ArithmeticException("First parameter is not valid");
		// System.out.println(s);

		System.out.println("please enter Mass:");
		double mass = scanner.nextDouble();
		if (mass <= 0)
			throw new ArithmeticException("Second parameter is not valid");
		
		System.out.println("please enter  μ:");
		double μ = scanner.nextDouble();
		if (μ < 0)
			throw new ArithmeticException("Third parameter is not valid");
	
		System.out
				.println("please select 0-5 decimal points you would like the answer in: ");
		int deci = scanner.nextInt();
		if (deci < 0 || deci > 5)
			throw new ArithmeticException("error with decimal points");

		double radians = Math.toRadians(degrees);
		double g = 9.80665;
		double mg = mass * g;
		double fd = mg * Math.sin(radians);
		double fu = mg * Math.cos(radians) * μ;
		double fn = fd - fu;
		double a = fn / mass;

		BigDecimal bdfd = new BigDecimal(fd);
		bdfd = bdfd.setScale(deci, BigDecimal.ROUND_HALF_UP);
		BigDecimal bdfu = new BigDecimal(fu);
		bdfu = bdfu.setScale(deci, BigDecimal.ROUND_HALF_UP);
		BigDecimal bdfn = new BigDecimal(fn);
		bdfn = bdfn.setScale(deci, BigDecimal.ROUND_HALF_UP);
		BigDecimal bda = new BigDecimal(a);
		bda = bda.setScale(deci, BigDecimal.ROUND_HALF_UP);

		System.out.format("The sin and cos of %.2f degrees is %.4f and %.4f, respectively%n",degrees, Math.sin(radians), Math.cos(radians));
		System.out.println("Force downwards is: " + bdfd);
		System.out.println("Force upwards is: " + bdfu);
		System.out.println("Net Force is: " + bdfn);
		System.out.println("Net Accerlation is: " + bda);

	}
}
/**
 * input values feta: 40 mass: 10 μ: .2
 * 
 * mg sin * - mg cos * μ = fn 
 * fn/m = a
 *  awn is: 
 * Fn = ... 
 * A = ...
 */
