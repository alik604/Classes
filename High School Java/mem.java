import java.lang.management.*;

public class mem {
	public static void main(String[] args) {
		// System.out.println(average(1, 2, 3, 4, 5, 6, 7, 8, 9));

		
		
		
		
		
	}// main

	public static int average(int... numbers) {
		int sum = 0;
		for (int x : numbers)
			sum += x;
		return sum / numbers.length;
	}
}

// https://docs.oracle.com/javase/7/docs/api/java/lang/management/MemoryUsage.html