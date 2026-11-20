package f1explorer;

public class Driver {

	// storing basic info about driver
	private int driverNumber;
	private String fullName;
	private String teamName;
	private String countryCode;

	// constructor to create driver object from api data
	public Driver(int driverNumber, String fullName, String teamName, String countryCode) {
		this.driverNumber = driverNumber;
		this.fullName = fullName;
		this.teamName = teamName;
		this.countryCode = countryCode;
	}

	// returning driver racing number
	public int getDriverNumber() {
		return driverNumber;
	}

	// returning full name (name + surname)
	public String getFullName() {
		return fullName;
	}

	// returning team name
	public String getTeamName() {
		return teamName;
	}

	// returning country code (for example NED, GBR, etc)
	public String getCountryCode() {
		return countryCode;
	}

	// printing driver info in readable format
	public void printDriver(int number) {

		// printing visual separator line
		UIHelper.printLine();

		// showing driver position in list + name
		System.out.println(number + ". " + fullName);

		// showing driver number
		System.out.println("Driver number: " + driverNumber);

		// colouring team name using helper (for nicer UI)
		System.out.println("Team: " + UIHelper.colourTeam(teamName));

		// printing country code (flag idea was dropped)
		System.out.println("Country: " + countryCode);
	}
}
