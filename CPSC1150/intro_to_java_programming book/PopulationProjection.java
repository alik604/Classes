package intro_to_java_programming;

public class PopulationProjection {
	public static void main(String[] args) {
		double birth = 7;
		double death = 13;
		double immi = 45;
		double popu = 312032486;
		double secs = 365 * 24 * 60 * 60;
		double new_popu = 0;
		for (int i = 0; i < 5; i++) {
			new_popu = (secs / birth) + (secs / immi) - (secs / death)
					+ new_popu;
			System.out.printf("pop of year %d is %d\n", i,
					(int) (new_popu + popu));
		}
		// /////////

		double birthRateInSeconds = 7.0;
		double deathRateInSeconds = 13.0;
		double newImmigrantInSeconds = 45.0;

		double currentPopulation = 312032486;

		double secondsInYears = 60 * 60 * 24 * 365;

		double numBirths = secondsInYears / birthRateInSeconds;
		double numDeaths = secondsInYears / deathRateInSeconds;
		double numImmigrants = secondsInYears / newImmigrantInSeconds;

		for (int i = 1; i <= 5; i++) {
			currentPopulation += numBirths + numImmigrants - numDeaths;
			System.out.println("Year " + i + " = " + (int) currentPopulation);

		}

	}
}
