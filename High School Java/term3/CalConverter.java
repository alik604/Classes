package term3;

import java.time.*;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.util.Calendar;
import java.util.Date;

/**
 * @author kali
 *
 * This is just me playing around trying to convert AD to AH.
 * I wanted to do it manually, but my dreams where crushed by lack of formulas
 * https://en.wikipedia.org/wiki/Hijri_year#Formula
 *
 *
 */
public class CalConverter {
	public static void main(String[] args) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		HijrahDate AHDate = HijrahChronology.INSTANCE.date(LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE)));
		System.out.println("Today in AD is "+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE) );
		System.out.println(AHDate);// year month day...
	}
}