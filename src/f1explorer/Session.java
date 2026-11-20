package f1explorer;

public class Session {

	// unique session id (used for api requests)
	private int sessionKey;

	// session name (e.g. Practice, Qualifying, Race)
	private String sessionName;

	// type/category of the session
	private String sessionType;

	// session start date-time (raw from api)
	private String dateStart;

	// session end date-time (raw from api)
	private String dateEnd;

	// constructor to create session object from api data
	public Session(int sessionKey, String sessionName, String sessionType, String dateStart, String dateEnd) {
		this.sessionKey = sessionKey;
		this.sessionName = sessionName;
		this.sessionType = sessionType;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	} // Session close

	// returning session key
	public int getSessionKey() {
		return sessionKey;
	} // getSessionKey close

	// returning session name
	public String getSessionName() {
		return sessionName;
	} // getSessionName close

	// returning session type
	public String getSessionType() {
		return sessionType;
	} // getSessionType close

	// returning start date-time
	public String getDateStart() {
		return dateStart;
	} // getDateStart close

	// returning end date-time
	public String getDateEnd() {
		return dateEnd;
	} // getDateEnd close

	// printing short session info in list format
	public void printSession(int number) {
		System.out.println("---------------------------------");

		// printing session number + name
		System.out.println(number + ". " + sessionName);

		// formatting and printing session time range
		System.out.println("Time: " + FormatHelper.formatDateTime(dateStart) 
				+ " - " + FormatHelper.formatDateTime(dateEnd));
	} // printSession close
}
