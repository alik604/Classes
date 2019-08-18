package ark_2016;
/**
 * Created by kali on 12/29/2015.		
 */

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

// web

public class thejollywatcher {

	public static void main(String args[]) {
		// throws IOException,URISyntaxException
		Switch();
	}

	public static void Switch() {

		Desktop d = Desktop.getDesktop();
		Calendar dateTime = Calendar.getInstance();
		dateTime.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// dateTime.setTimeInMillis(1413831601032L);
		// dateTime.setFirstDayOfWeek(dateTime.MONDAY); // useless crap
		// System.out.print(dateTime.get(dateTime.DAY_OF_WEEK));

		// String a
		// ="magnet:?xt=urn:btih:6196923B8FDB84DB3CC157B30E62BA4A5F56671D&dn=spectre+2015+dvdscr+xvid+ift&tr=udp%3A%2F%2Ftracker.publicbt.com%3A80%2Fannounce&tr=udp%3A%2F%2Fglotorrents.pw%3A6969%2Fannounce";
		// d.browse(new URI(a));

		try {

			switch (dateTime.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
				d.browse(new URI(
						"https://kat.cr/usearch/Brooklyn%20Nine-Nine%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/family%20guy%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/Quantico%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/simpsons%20category%3Atv/?field=time_add&sorder=desc"));

				break;

			case Calendar.MONDAY:

				d.browse(new URI(
						"https://kat.cr/usearch/Scorpion%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/Gotham%20category%3Atv//?field=time_add&sorder=desc"));
				break;
			case Calendar.TUESDAY:

				d.browse(new URI(
						"https://kat.cr/usearch/The%20Flash%20category%3Atv//?field=time_add&sorder=desc"));
				break;

			case Calendar.WEDNESDAY:
				d.browse(new URI(
						"https://kat.cr/usearch/Blackish%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/Arrow%20category%3Atv//?field=time_add&sorder=desc"));
				break;

			case Calendar.THURSDAY:
				d.browse(new URI(
						"https://kat.cr/usearch/Gotham%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/How%20to%20Get%20Away%20with%20Murder%20category%3Atv//?field=time_add&sorder=desc"));
				break;

			case Calendar.FRIDAY:

				break;

			case Calendar.SATURDAY:

				break;

			}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}

	}
}