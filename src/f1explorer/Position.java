package f1explorer;

public class Position {

	// storing basic info about driver position in session
	private int driverNumber;
	private int position;
	private String driverName;
	private String teamName;

	// constructor to create position object
	public Position(int driverNumber, int position, String driverName, String teamName) {
		this.driverNumber = driverNumber;
		this.position = position;
		this.driverName = driverName;
		this.teamName = teamName;
	} // Position constructor close

	// returning driver number
	public int getDriverNumber() {
		return driverNumber;
	} // getDriverNumber close

	// returning position (P1, P2, etc)
	public int getPosition() {
		return position;
	} // getPosition close

	// returning driver name
	public String getDriverName() {
		return driverName;
	} // getDriverName close

	// returning team name
	public String getTeamName() {
		return teamName;
	} // getTeamName close

	// printing formatted position info
	public void printPosition(boolean favourite) {

		// getting nice formatted label (with colors for podium)
		String label = UIHelper.getPositionLabel(position);

		// if this driver is favourite, we highlight it
		if (favourite) {
			System.out.println(label + " " + driverName + " (" + driverNumber + ") " + UIHelper.favouriteText());
		} else {
			System.out.println(label + " " + driverName + " (" + driverNumber + ")");
		}

		// printing team with colors
		System.out.println("Team: " + UIHelper.colourTeam(teamName));

		// separator line
		UIHelper.printLine();
	} // printPosition close
}
