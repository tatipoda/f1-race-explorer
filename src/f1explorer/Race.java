package f1explorer;

public class Race {

	// unique race/meeting id used for api calls
	private int meetingKey;

	// race name
	private String meetingName;

	// city/track location
	private String location;

	// country where race takes place
	private String countryName;

	// race start date-time from api
	private String dateStart;

	// true if race was cancelled
	private boolean cancelled;

	// constructor to create race object from api data
	public Race(int meetingKey, String meetingName, String location, String countryName, String dateStart,
			boolean cancelled) {

		this.meetingKey = meetingKey;
		this.meetingName = meetingName;
		this.location = location;
		this.countryName = countryName;
		this.dateStart = dateStart;
		this.cancelled = cancelled;
	} // Race close

	// returning meeting key
	public int getMeetingKey() {
		return meetingKey;
	} // getMeetingKey close

	// returning race name
	public String getMeetingName() {
		return meetingName;
	} // getMeetingName close

	// returning race location
	public String getLocation() {
		return location;
	} // getLocation close

	// returning country name
	public String getCountryName() {
		return countryName;
	} // getCountryName close

	// returning start date-time
	public String getDateStart() {
		return dateStart;
	} // getDateStart close

	// returning race status
	public boolean isCancelled() {
		return cancelled;
	} // isCancelled close

	// printing race information in readable format
	public void printRace(int number) {
		System.out.println("---------------------------------");
		System.out.println(number + ". " + meetingName);
		System.out.println("Location: " + location + ", " + countryName);
		System.out.println("Start date: " + FormatHelper.formatDateTime(dateStart));

		// colouring status so cancelled races stand out
		System.out.println("Status: " + UIHelper.colourStatus(cancelled));
	} // printRace close
}

