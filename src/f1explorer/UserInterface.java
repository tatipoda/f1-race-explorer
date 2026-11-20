package f1explorer;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

	// scanner for reading user input from console
	private Scanner scanner;

	// controls if main menu keeps running
	private boolean running;

	// object that handles all api requests
	private ApiHandler apiHandler;

	// stores user settings like default year and favourite driver
	private Settings settings;

	// object that saves and loads settings/cache files
	private FileHandler fileHandler;

	public UserInterface(Scanner scanner) {
		this.scanner = scanner;
		running = true;
		apiHandler = new ApiHandler();
		fileHandler = new FileHandler();

		// loading saved settings when app starts
		settings = fileHandler.loadSettings();
	}

	// main program loop
	public void start() {

		// keeps showing menu until user chooses exit
		while (running) {
			showMainMenu();

			// reading user menu choice
			String choice = scanner.nextLine();

			handleMainMenuChoice(choice);
		}
	} // start close

	// printing main menu
	private void showMainMenu() {
		UIHelper.printMainMenuBox();
		System.out.print("Enter your choice: ");
	} // showMainMenu close

	// deciding what to do based on main menu input
	private void handleMainMenuChoice(String choice) {

		switch (choice) {

		case "1":
			UIHelper.clearScreen();
			browseRacesByYear();
			break;

		case "2":
			UIHelper.clearScreen();
			showAllDrivers();
			break;

		case "3":
			UIHelper.clearScreen();
			searchDriver();
			break;

		case "4":
			UIHelper.clearScreen();
			showSettingsMenu();
			break;

		case "5":
			UIHelper.clearScreen();
			UIHelper.printGoodbye();

			// stopping main loop
			running = false;
			break;

		default:
			System.out.println("Invalid input. Please enter a number from 1 to 5.");
		}
	} // handleMainMenuChoice close

	// asking user for year and loading race list
	private void browseRacesByYear() {
		System.out.println();
		System.out.print(
				"Enter year from 2023 to 2026, or press Enter for default year " + settings.getDefaultYear() + ": ");
		String year = scanner.nextLine();

		// if user just presses enter, use saved default year
		if (year.isEmpty()) {
			year = settings.getDefaultYear();
		}

		// checking that year is between 2023 and 2026
		if (year.matches("202[3-6]")) {

			System.out.println();
			System.out.println("Loading races for " + year + "...");

			// getting races from api
			ArrayList<Race> races = apiHandler.getRacesByYear(year);

			if (races.size() == 0) {
				System.out.println("No races found.");
			} else {

				// printing races and letting user choose one
				printRaces(year, races);
				chooseRace(races);
			}

		} else {
			System.out.println("Invalid year. Please enter a year from 2023 to 2026.");
		}
	} // browseRacesByYear close

	// printing races in two columns so the list is not too long
	private void printRaces(String year, ArrayList<Race> races) {
		System.out.println();
		UIHelper.printHeader("Races in " + year);

		// going through races two at a time
		for (int i = 0; i < races.size(); i = i + 2) {
			Race leftRace = races.get(i);
			Race rightRace = null;

			// checking if second race exists
			if (i + 1 < races.size()) {
				rightRace = races.get(i + 1);
			}

			printRaceRow(i + 1, leftRace, i + 2, rightRace);
		}
	} // printRaces close

	// printing one row of races, left race and maybe right race
	private void printRaceRow(int leftNumber, Race leftRace, int rightNumber, Race rightRace) {

		String leftStatus = UIHelper.colourStatus(leftRace.isCancelled());

		String leftLine1 = leftNumber + ". " + leftRace.getMeetingName();
		String leftLine2 = "Location: " + leftRace.getLocation() + ", " + leftRace.getCountryName();
		String leftLine3 = "Date: " + FormatHelper.formatDateTime(leftRace.getDateStart());
		String leftLine4 = "Status: " + leftStatus;

		// if there is right race, print two races side by side
		if (rightRace != null) {
			String rightStatus = UIHelper.colourStatus(rightRace.isCancelled());

			String rightLine1 = rightNumber + ". " + rightRace.getMeetingName();
			String rightLine2 = "Location: " + rightRace.getLocation() + ", " + rightRace.getCountryName();
			String rightLine3 = "Date: " + FormatHelper.formatDateTime(rightRace.getDateStart());
			String rightLine4 = "Status: " + rightStatus;

			// %-45s means left aligned string with width 45
			System.out.printf("%-45s | %-45s%n", leftLine1, rightLine1);
			System.out.printf("%-45s | %-45s%n", leftLine2, rightLine2);
			System.out.printf("%-45s | %-45s%n", leftLine3, rightLine3);
			System.out.printf("%-45s          | %-45s%n", leftLine4, rightLine4);
			System.out.println(UIHelper.CYAN + "------------------------------------------------------------------------------------------" + UIHelper.RESET);

		} else {
			// if there is only one race left, print it alone
			System.out.println(leftLine1);
			System.out.println(leftLine2);
			System.out.println(leftLine3);
			System.out.println(leftLine4);
			System.out.println(UIHelper.CYAN + "------------------------------------" + UIHelper.RESET);
		}
	} // printRaceRow close

	// asking user which race they want to open
	private void chooseRace(ArrayList<Race> races) {
		System.out.println();
		System.out.print("Choose race number to view sessions, or enter 0 to go back: ");
		String input = scanner.nextLine();

		// checking input is numeric
		if (input.matches("[0-9]+")) {

			int choice;

			try {
   				 choice = Integer.parseInt(input);
			} catch (NumberFormatException e) {
 			   System.out.println("Invalid number.");
    			return;
			}

			// 0 sends user back
			if (choice == 0) {
				return;
			}

			// checking that selected race exists in list
			if (choice >= 1 && choice <= races.size()) {
				Race selectedRace = races.get(choice - 1);
				showSessionsForRace(selectedRace);
			} else {
				System.out.println("Invalid race number.");
			}

		} else {
			System.out.println("Invalid input. Please enter a number.");
		}
	} // chooseRace close

	// loading sessions for selected race
	private void showSessionsForRace(Race race) {
		System.out.println();
		System.out.println("Loading sessions for " + race.getMeetingName() + "...");

		ArrayList<Session> sessions = apiHandler.getSessionsByMeetingKey(race.getMeetingKey());

		if (sessions.size() == 0) {
			System.out.println("No sessions found.");
		} else {
			System.out.println();

			printSessions(sessions);

			chooseSession(sessions);
		}
	} // showSessionsForRace close

	// printing sessions in two columns
	private void printSessions(ArrayList<Session> sessions) {
		UIHelper.printHeader("Sessions");

		for (int i = 0; i < sessions.size(); i = i + 2) {
			Session leftSession = sessions.get(i);
			Session rightSession = null;

			if (i + 1 < sessions.size()) {
				rightSession = sessions.get(i + 1);
			}

			printSessionRow(i + 1, leftSession, i + 2, rightSession);
		}
	} // printSessions close

	// printing drivers in two columns
	private void printDriversCompact(ArrayList<Driver> drivers) {
		UIHelper.printHeader("Drivers");

		for (int i = 0; i < drivers.size(); i = i + 2) {
			Driver leftDriver = drivers.get(i);
			Driver rightDriver = null;

			if (i + 1 < drivers.size()) {
				rightDriver = drivers.get(i + 1);
			}

			printDriverRow(i + 1, leftDriver, i + 2, rightDriver);
		}
	} // printDriversCompact close

	// printing one row of drivers
	private void printDriverRow(int leftNumber, Driver leftDriver, int rightNumber, Driver rightDriver) {

		String leftFavourite = "";

		// marking favourite driver in list
		if (leftDriver.getDriverNumber() == settings.getFavouriteDriverNumber()) {
			leftFavourite = " <-- FAVOURITE";
		}

		String leftLine1 = leftNumber + ". " + leftDriver.getFullName() + leftFavourite;
		String leftLine2 = "No: " + leftDriver.getDriverNumber();
		String leftLine3 = "Team: " + leftDriver.getTeamName();
		
		if (rightDriver != null) {
			String rightFavourite = "";

			if (rightDriver.getDriverNumber() == settings.getFavouriteDriverNumber()) {
				rightFavourite = " <-- FAVOURITE";
			}

			String rightLine1 = rightNumber + ". " + rightDriver.getFullName() + rightFavourite;
			String rightLine2 = "No: " + rightDriver.getDriverNumber();
			String rightLine3 = "Team: " + rightDriver.getTeamName();

			System.out.printf("%-45s | %-45s%n", leftLine1, rightLine1);
			System.out.printf("%-45s | %-45s%n", leftLine2, rightLine2);
			System.out.printf("%-45s | %-45s%n", leftLine3, rightLine3);
			System.out.println(UIHelper.CYAN + "------------------------------------------------------------------------------------------" + UIHelper.RESET);

		} else {
			System.out.println(leftLine1);
			System.out.println(leftLine2);
			System.out.println(leftLine3);
			System.out.println(UIHelper.CYAN + "------------------------------------" + UIHelper.RESET);
		}
	} // printDriverRow close

	// printing one row of sessions
	private void printSessionRow(int leftNumber, Session leftSession, int rightNumber, Session rightSession) {

		String leftLine1 = leftNumber + ". " + leftSession.getSessionName();
		String leftLine2 = "Time: " + FormatHelper.formatDateTime(leftSession.getDateStart()) 
				+ " - " + FormatHelper.formatDateTime(leftSession.getDateEnd());

		if (rightSession != null) {
			String rightLine1 = rightNumber + ". " + rightSession.getSessionName();
			String rightLine2 = "Time: " + FormatHelper.formatDateTime(rightSession.getDateStart()) 
					+ " - " + FormatHelper.formatDateTime(rightSession.getDateEnd());

			System.out.printf("%-45s | %-45s%n", leftLine1, rightLine1);
			System.out.printf("%-45s | %-45s%n", leftLine2, rightLine2);
			System.out.println(UIHelper.CYAN + "------------------------------------------------------------------------------------------" + UIHelper.RESET);

		} else {
			System.out.println(leftLine1);
			System.out.println(leftLine2);
			System.out.println(UIHelper.CYAN + "------------------------------------" + UIHelper.RESET);
		}
	} // printSessionRow close

	// asking user which session they want to open
	private void chooseSession(ArrayList<Session> sessions) {
		System.out.println();
		System.out.print("Choose session number, or enter 0 to go back: ");
		String input = scanner.nextLine();

		if (input.matches("[0-9]+")) {

			int choice;

			try {
   				 choice = Integer.parseInt(input);
			} catch (NumberFormatException e) {
 			   System.out.println("Invalid number.");
    			return;
			}

			if (choice == 0) {
				return;
			}

			if (choice >= 1 && choice <= sessions.size()) {
				Session selectedSession = sessions.get(choice - 1);
				showSessionMenu(selectedSession);
			} else {
				System.out.println("Invalid session number.");
			}

		} else {
			System.out.println("Invalid input. Please enter a number.");
		}
	} // chooseSession close

	// menu for selected session
	private void showSessionMenu(Session session) {
		boolean inSessionMenu = true;

		while (inSessionMenu) {
			UIHelper.printSessionMenuWithArt(session.getSessionName());
			System.out.print("Enter your choice: ");

			String choice = scanner.nextLine();

			switch (choice) {

			case "1":
				showDriversInSession(session);
				break;

			case "2":
				showSessionPositions(session);
				break;

			case "3":
				searchDriverLapTimes(session);
				break;

			case "4":
				printSelectedSessionInfo(session);
				break;

			case "5":
				UIHelper.clearScreen();
				inSessionMenu = false;
				break;

			default:
				System.out.println("Invalid input. Please enter a number from 1 to 5.");
			}
		}
	} // showSessionMenu close

	// printing basic information about selected session
	private void printSelectedSessionInfo(Session session) {
		System.out.println();
		System.out.println("========== Session Details ==========");
		System.out.println("Name: " + session.getSessionName());
		System.out.println("Category: " + session.getSessionType());
		System.out.println("Start: " + FormatHelper.formatDateTime(session.getDateStart()));
		System.out.println("End: " + FormatHelper.formatDateTime(session.getDateEnd()));

		System.out.println();
		System.out.println("Available actions:");
		System.out.println("- View drivers in this session");
		System.out.println("- Search lap times by driver");
		System.out.println("- Compare fastest and average lap");

		pause();
	} // printSelectedSessionInfo close

	// printing driver and marking him if he is favourite
	private void printDriverWithFavouriteMark(Driver driver, int number) {

		UIHelper.printLine();

		if (driver.getDriverNumber() == settings.getFavouriteDriverNumber()) {
			System.out.println(number + ". " + driver.getFullName() + " " + UIHelper.favouriteText());
		} else {
			System.out.println(number + ". " + driver.getFullName());
		}

		System.out.println("Driver number: " + driver.getDriverNumber());
		System.out.println("Team: " + UIHelper.colourTeam(driver.getTeamName()));
		System.out.println("Country: " + driver.getCountryCode());
	} // printDriverWithFavouriteMark close
	
	// showing final/current positions for selected session
	private void showSessionPositions(Session session) {

		// if session is in the future, results cannot exist yet
		if (FormatHelper.isFutureDateTime(session.getDateStart())) {
			System.out.println();
			System.out.println("Session results are not available yet.");
			System.out.println("This session has not happened yet.");
			pause();
			return;
		}

		System.out.println();
		System.out.println("Loading positions for " + session.getSessionName() + "...");

		// loading positions from api/cache
		ArrayList<Position> positions = apiHandler.getPositionsBySessionKey(session.getSessionKey());

		if (positions.size() == 0) {
			System.out.println("No position data found for this session.");
			pause();
			return;
		}

		System.out.println();
		System.out.println("========== Session Positions ==========");

		// printing small podium if we have top 3 drivers
		if (positions.size() >= 3) {
			UIHelper.printPodium(positions.get(0), positions.get(1), positions.get(2));
		}

		// printing all positions and checking favourite driver
		for (int i = 0; i < positions.size(); i++) {
			boolean favourite = positions.get(i).getDriverNumber() == settings.getFavouriteDriverNumber();
			positions.get(i).printPosition(favourite);
		}

		pause();
	} // showSessionPositions close

	
	// showing drivers who are listed in selected session
	private void showDriversInSession(Session session) {
		System.out.println();
		System.out.println("Loading drivers for " + session.getSessionName() + "...");

		ArrayList<Driver> drivers = apiHandler.getDriversBySessionKey(session.getSessionKey());

		if (drivers.size() == 0) {
			System.out.println("No drivers found.");
		} else {
			printDriversCompact(drivers);
		}
		pause();
	} // showDriversInSession close

	// showing all unique drivers from api
	private void showAllDrivers() {
		System.out.println();
		System.out.println("Loading all drivers...");

		ArrayList<Driver> drivers = apiHandler.getAllDrivers();

		if (drivers.size() == 0) {
			System.out.println("No drivers found.");
		} else {
			printDriversCompact(drivers);
		}
		pause();
	} // showAllDrivers close

	// searching driver by name or racing number
	private void searchDriver() {
		System.out.println();
		System.out.print("Enter driver name or number: ");
		String search = scanner.nextLine();

		ArrayList<Driver> drivers = apiHandler.getAllDrivers();

		if (drivers.size() == 0) {
			System.out.println("No drivers available.");
			pause();
			return;
		}

		boolean found = false;

		System.out.println();
		System.out.println("========== Search Results ==========");

		// checking every driver in list
		for (int i = 0; i < drivers.size(); i++) {

			Driver driver = drivers.get(i);

			String driverNumber = Integer.toString(driver.getDriverNumber());
			String driverName = driver.getFullName().toLowerCase();
			String searchLower = search.toLowerCase();

			// matching exact number or part of name
			if (driverNumber.equals(search) || driverName.contains(searchLower)) {
				printDriverWithFavouriteMark(driver, i + 1);
				found = true;
			}
		}

		if (!found) {
			System.out.println("No matching driver found.");
		}
		pause();
	} // searchDriver close

	// finding driver by name or number inside a given list
	private Driver findDriverByNameOrNumber(ArrayList<Driver> drivers, String input) {

		String inputLower = input.toLowerCase();

		for (int i = 0; i < drivers.size(); i++) {
			Driver driver = drivers.get(i);

			String driverNumber = Integer.toString(driver.getDriverNumber());
			String driverName = driver.getFullName().toLowerCase();

			if (driverNumber.equals(input) || driverName.contains(inputLower)) {
				return driver;
			}
		}

		return null;
	} // findDriverByNameOrNumber close
	
	// finding driver only by racing number
	private Driver findDriverByNumber(ArrayList<Driver> drivers, int driverNumber) {

		for (int i = 0; i < drivers.size(); i++) {
			if (drivers.get(i).getDriverNumber() == driverNumber) {
				return drivers.get(i);
			}
		}

		return null;
	} // findDriverByNumber close

	// searching lap times for selected driver in selected session
	private void searchDriverLapTimes(Session session) {
		System.out.println();

		// first loading drivers so user can search by name or number
		ArrayList<Driver> drivers = apiHandler.getDriversBySessionKey(session.getSessionKey());

		if (drivers.size() == 0) {
			System.out.println("No drivers available for this session.");
			pause();
			return;
		}

		System.out.print("Enter driver name, surname, racing number, or press Enter for favourite ("
				+ settings.getFavouriteDriverNumber() + "): ");
		String input = scanner.nextLine();

		Driver selectedDriver;

		// empty input means using favourite driver
		if (input.isEmpty()) {
			selectedDriver = findDriverByNumber(drivers, settings.getFavouriteDriverNumber());
		} else {
			selectedDriver = findDriverByNameOrNumber(drivers, input);
		}

		if (selectedDriver == null) {
			System.out.println("Driver was not found in this session.");
			pause();
			return;
		}

		if (selectedDriver.getDriverNumber() == settings.getFavouriteDriverNumber()) {
			System.out.println("Favourite driver selected!");
		}

		System.out.println();
		System.out.println("Loading lap times for " + selectedDriver.getFullName() + "...");

		// loading laps for exact session and driver number
		ArrayList<Lap> laps = apiHandler.getLapsBySessionAndDriver(
				session.getSessionKey(),
				selectedDriver.getDriverNumber()
		);

		if (laps.size() == 0) {
			System.out.println("No lap data found for " + selectedDriver.getFullName() + " in this session.");
			pause();
			return;
		}

		System.out.println();
		System.out.println("========== Lap Times ==========");
		System.out.println("Driver: " + selectedDriver.getFullName());
		System.out.println("Team: " + selectedDriver.getTeamName());

		for (int i = 0; i < laps.size(); i++) {
			laps.get(i).printLap();
		}

		printLapSummary(laps);
		pause();
	} // searchDriverLapTimes close


	// calculating fastest lap and average lap
	private void printLapSummary(ArrayList<Lap> laps) {

		double fastest = laps.get(0).getLapDuration();
		double total = 0;

		// going through all laps to get total and fastest
		for (int i = 0; i < laps.size(); i++) {
			double lapTime = laps.get(i).getLapDuration();

			total = total + lapTime;

			if (lapTime < fastest) {
				fastest = lapTime;
			}
		}

		double average = total / laps.size();

		System.out.println();
		System.out.println("========== Summary ==========");
		System.out.println("Total laps: " + laps.size());
		System.out.println("Fastest lap: " + FormatHelper.formatLapTime(fastest));
		System.out.println("Average lap: " + FormatHelper.formatLapTime(average));
	} // printLapSummary close

	// small pause so user can read output before menu continues
	private void pause() {
		System.out.println();
		System.out.print("Press Enter to continue...");
		scanner.nextLine();
	} // pause close

	// changing default year setting
	private void changeDefaultYear() {
		System.out.println();
		System.out.print("Enter default year from 2023 to 2026: ");
		String year = scanner.nextLine();

		// checking that year is supported by OpenF1 data
		if (year.matches("202[3-6]")) {
			settings.setDefaultYear(year);
			fileHandler.saveSettings(settings);
			System.out.println("Default year changed to " + year);
			System.out.println("Settings saved.");
		} else {
			System.out.println("Invalid year. Please enter a year from 2023 to 2026.");
		}
	} // changeDefaultYear close

	// changing favourite driver setting
	private void changeFavouriteDriver() {
		System.out.println();
		System.out.println("Select your favourite driver from the list below:");

		ArrayList<Driver> drivers = apiHandler.getAllDrivers();

		if (drivers.size() == 0) {
			System.out.println("No drivers available.");
			pause();
			return;
		}

		// showing drivers as numbered list
		for (int i = 0; i < drivers.size(); i++) {
			System.out.println((i + 1) + ". " + drivers.get(i).getFullName()
					+ " (" + drivers.get(i).getDriverNumber() + ")");
		}

		System.out.print("Enter list number to select favourite driver: ");
		String input = scanner.nextLine();

		if (input.matches("[0-9]+")) {

			int choice;

			try {
   				 choice = Integer.parseInt(input);
			} catch (NumberFormatException e) {
 			   System.out.println("Invalid number.");
    			return;
			}

			// checking if selected list number exists
			if (choice >= 1 && choice <= drivers.size()) {

				Driver selected = drivers.get(choice - 1);

				settings.setFavouriteDriverNumber(selected.getDriverNumber());
				fileHandler.saveSettings(settings);

				System.out.println("Favourite driver set to " + selected.getFullName());
				System.out.println("Settings saved.");

			} else {
				System.out.println("Invalid choice.");
			}

		} else {
			System.out.println("Invalid input. Please enter a number.");
		}

		pause();
	} // changeFavouriteDriver close


	// settings menu
	private void showSettingsMenu() {
		boolean inSettings = true;

		while (inSettings) {
			System.out.println();
			System.out.println("========== Settings ==========");
			System.out.println();
			System.out.println("Current default year: " + settings.getDefaultYear());
			System.out.println("Favourite driver number: " + settings.getFavouriteDriverNumber());
			System.out.println();
			System.out.println("1. Change default year");
			System.out.println("2. Change favourite driver");
			System.out.println("3. Back");
			System.out.print("Enter your choice: ");

			String choice = scanner.nextLine();

			switch (choice) {

			case "1":
				changeDefaultYear();
				break;

			case "2":
				changeFavouriteDriver();
				break;

			case "3":
				UIHelper.clearScreen();
				inSettings = false;
				break;

			default:
				System.out.println("Invalid input. Please enter 1, 2 or 3.");
			}
		}
	} // showSettingsMenu close
}
