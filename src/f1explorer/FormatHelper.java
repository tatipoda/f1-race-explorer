package f1explorer;

import java.time.OffsetDateTime;
import java.util.Locale;

public class FormatHelper {

	// checking if given datetime is in the future
	public static boolean isFutureDateTime(String dateTime) {
		try {
			// parsing string into datetime object
			OffsetDateTime sessionTime = OffsetDateTime.parse(dateTime);

			// getting current time
			OffsetDateTime now = OffsetDateTime.now();

			// comparing times
			return sessionTime.isAfter(now);

		} catch (Exception e) {
			// if parsing failed — просто считаем что не future
			return false;
		}
	} // isFutureDateTime close

	
	// formatting datetime string into readable format dd/mm/yyyy hh:mm
	public static String formatDateTime(String dateTime) {

		// basic validation, чтобы не словить ошибку substring
		if (dateTime == null || dateTime.length() < 16) {
			return "Unknown date";
		}

		// splitting string manually (быстро и без лишних либ)
		String year = dateTime.substring(0, 4);
		String month = dateTime.substring(5, 7);
		String day = dateTime.substring(8, 10);
		String time = dateTime.substring(11, 16);

		// returning formatted string
		return day + "/" + month + "/" + year + " " + time;
	} // formatDateTime close

	// formatting lap time from seconds to mm:ss.sss
	public static String formatLapTime(double seconds) {

    long totalMillis = Math.round(seconds * 1000);

    long minutes = totalMillis / 60000;
    long remainingSeconds = (totalMillis / 1000) % 60;
    long millis = totalMillis % 1000;

    return String.format(Locale.ROOT, "%d:%02d.%03d",
            minutes, remainingSeconds, millis);
}
}
