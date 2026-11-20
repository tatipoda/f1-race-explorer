package f1explorer;

public class UIHelper {

	// ansi colour codes for console styling
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";
	public static final String BRIGHT_RED = "\u001B[91m";
	public static final String BRIGHT_GREEN = "\u001B[92m";
	public static final String BRIGHT_YELLOW = "\u001B[93m";
	public static final String BRIGHT_BLUE = "\u001B[94m";
	public static final String BRIGHT_PURPLE = "\u001B[95m";
	public static final String BRIGHT_CYAN = "\u001B[96m";
	public static final String BLACK = "\u001B[30m";

	// printing intro screen when app starts
	public static void printIntro() {
		String m = "  ";

		System.out.println();

		// big f1 car ascii art
		System.out.println(BRIGHT_BLUE + m + "⠀⠀⢀⣀⣀⣠⣤⣤⣤⣶⣶⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
		System.out.println(BRIGHT_BLUE + m + "⠀⣼⡿⢿⣿⣿⣿⣿⣿⣿⣯⣿⣿⡏⣀⣤⣤⣶⣶⣶⣶⣾⣿⣿⣤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
		System.out.println(BRIGHT_BLUE + m + "⠀⠙⣿⣷⣿⣄⢈⣀⣠⣼⣯⣹⣋⣹⣿⣿⣯⣥⣾⣟⣹⣿⣾⣿⣏⣀⣠⣤⣤⣤⣤⣤⡀⠀⢀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
		System.out.println(BRIGHT_BLUE + m + "⢠⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣭⣙⡟⠛⠿⠿⠿⠿⠻⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣴⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
		System.out.println(RED + m + "⣿⣿⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣟⣀⣠⣤⣶⣿⣿⣿⣶⣿⣿⣿⣿⣿⣿⣿⣿⡿⢿⣿⣧⡒⠶⠦⣤⣀⠀⠀⠀⣠⣾⣿⣯⣟⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
		System.out.println(BRIGHT_BLUE + m + "⢹⣞⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣟⠉⠻⢤⣀⠀⣶⣴⣦⣶⡶⡆⢠⠿⣿⢿⣿⣿⣿⣿⣷⣶⣬⣍⣛⣶⣾⣭⣻⢿⣿⠿⣿⣿⣿⣿⣿⣿⣆⡇⠀⣰⣶⡤⣤⣄⠀⠀⠀⠀");
		System.out.println(BRIGHT_BLUE + m + "⠀⠛⣻⣿⣿⣿⡿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣬⣿⣿⣷⣼⣬⣿⣿⣿⣿⣿⣤⣽⣿⣿⣿⣷⣾⣿⣯⣭⣷⣿⣿⣿⠿⣿⣶⣤⣘⡙⠛⠿⣿⣿⠇⠀⣿⣿⣗⣿⣿⣆⠀⠀⠀");
		System.out.println(BRIGHT_BLUE + m + "⠀⠀⠀⠉⠉⠁⠀⠀⠀⠀⠀⠉⠛⠺⠿⢿⣿⣿⣿⣾⣿⣿⣿⠿⢿⣿⣿⣿⣿⣿⣿⣿⡿⢿⣿⣾⣿⣿⣶⣶⣽⡻⣧⠈⢻⣯⣽⣿⣷⣶⠾⣍⣦⠸⢿⣿⣿⣿⣿⣿⡄⠀⠀");
		System.out.println(BRIGHT_BLUE + m + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠛⠿⠿⢿⣿⣿⣿⣿⣟⣏⣛⣛⣷⣾⣿⣿⡿⣡⣶⣬⡹⣷⣽⣇⣠⣯⠉⠉⠙⠛⠷⣿⣽⣿⣦⣤⡙⠻⢿⣟⣧⡀⠀");
		System.out.println(BLUE + m + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⢹⣿⣇⢿⣿⣿⡇⣿⣿⣿⣿⣿⠋⠛⢲⡶⢾⣿⣿⡿⣿⣿⣿⣷⣦⡘⣿⣷⡀");
		System.out.println(BLUE + m + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⣿⣷⣤⣭⣾⣿⣿⣿⠟⢹⣘⣾⣿⣆⣤⣿⣗⣿⣿⣿⣿⣿⣿⣿⡿⠟⠃");
		System.out.println(BLUE + m + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠲⠾⣽⣿⡿⠟⠉⠀⠸⠿⢾⣭⣟⣻⠟⢹⣿⣿⣿⣿⠿⠛⠉⠀⠀⠀" + RESET);

		System.out.println();

		// welcome text under the car
		System.out.println(WHITE + m + "╻ ╻┏━╸╻  ┏━╸┏━┓┏┳┓┏━╸   ╺┳╸┏━┓  " + BLACK + "┏━╸" + RED + "╺┓" + WHITE
				+ "    ┏━┓┏━┓┏━╸┏━╸   ┏━╸╻ ╻┏━┓╻  ┏━┓┏━┓┏━╸┏━┓");
		System.out.println(WHITE + m + "┃╻┃┣╸ ┃  ┃  ┃ ┃┃┃┃┣╸     ┃ ┃ ┃  " + BLACK + "┣╸ " + RED + " ┃" + WHITE
				+ "    ┣┳┛┣━┫┃  ┣╸    ┣╸ ┏╋┛┣━┛┃  ┃ ┃┣┳┛┣╸ ┣┳┛");
		System.out.println(WHITE + m + "┗┻┛┗━╸┗━╸┗━╸┗━┛╹ ╹┗━╸    ╹ ┗━┛  " + BLACK + "╹  " + RED + "╺┻╸" + WHITE
				+ "   ╹┗╸╹ ╹┗━╸┗━╸   ┗━╸╹ ╹╹  ┗━╸┗━┛╹┗╸┗━╸╹┗╸" + RESET);

		System.out.println();
	} // printIntro close

	// printing exit message
	public static void printGoodbye() {
		System.out.println();
		System.out.println(BRIGHT_YELLOW + "Thanks for using F1 Race Explorer!");
		System.out.println("See you at the next Grand Prix." + RESET);
	} // printGoodbye close

	// printing reusable title box
	public static void printHeader(String title) {
		System.out.println();
		System.out.println(CYAN + "╔════════════════════════════════════════════════════════════════════════╗");
		System.out.printf("║ %-70s ║%n", title);
		System.out.println("╚════════════════════════════════════════════════════════════════════════╝" + RESET);
	} // printHeader close

	// printing main menu with small f1 logo
	public static void printMainMenuBox() {
		System.out.println();
		System.out.println(RED + "  ███████╗ " + WHITE + "██╗        " + CYAN + "╔════════════════════════════╗");
		System.out.println(RED + "  ██╔════╝" + WHITE + "███║        " + CYAN + "║      F1 RACE EXPLORER      ║");
		System.out.println(RED + "  █████╗  " + WHITE + "╚██║        " + CYAN + "╠════════════════════════════╣");
		System.out.println(RED + "  ██╔══╝   " + WHITE + "██║        " + CYAN + "║ 1. Browse races by year    ║");
		System.out.println(RED + "  ██║      " + WHITE + "██║        " + CYAN + "║ 2. Show all drivers        ║");
		System.out.println(RED + "  ╚═╝      " + WHITE + "╚═╝        " + CYAN + "║ 3. Search driver           ║");
		System.out.println("                    " + CYAN + "  ║ 4. Settings                ║");
		System.out.println("                    " + CYAN + "  ║ 5. Exit                    ║");
		System.out.println("                    " + CYAN + "  ╚════════════════════════════╝" + RESET);
	} // printMainMenuBox close

	// simple separator line
	public static void printLine() {
		System.out.println(CYAN + "------------------------------------" + RESET);
	} // printLine close

	// text used to mark favourite driver
	public static String favouriteText() {
		return BRIGHT_YELLOW + "<-- FAVOURITE DRIVER" + RESET;
	} // favouriteText close

	// returning coloured position label for results
	public static String getPositionLabel(int position) {
		if (position == 1) {
			return BRIGHT_YELLOW + "[ P1 ]" + RESET;
		} else if (position == 2) {
			return WHITE + "[ P2 ]" + RESET;
		} else if (position == 3) {
			return YELLOW + "[ P3 ]" + RESET;
		} else {
			return "[ P" + position + " ]";
		}
	} // getPositionLabel close

	// colouring team name depending on team
	public static String colourTeam(String teamName) {
		if (teamName == null) {
			return "Unknown team";
		}

		String lowerTeam = teamName.toLowerCase();

		if (lowerTeam.contains("ferrari")) {
			return BRIGHT_RED + teamName + RESET;
		} else if (lowerTeam.contains("red bull")) {
			return BLUE + teamName + RESET;
		} else if (lowerTeam.contains("mercedes")) {
			return WHITE + teamName + RESET;
		} else if (lowerTeam.contains("mclaren")) {
			return YELLOW + teamName + RESET;
		} else if (lowerTeam.contains("alpine")) {
			return CYAN + teamName + RESET;
		} else if (lowerTeam.contains("aston martin")) {
			return GREEN + teamName + RESET;
		} else if (lowerTeam.contains("williams")) {
			return BLUE + teamName + RESET;
		} else if (lowerTeam.contains("haas")) {
			return WHITE + teamName + RESET;
		} else if (lowerTeam.contains("alphatauri")) {
			return PURPLE + teamName + RESET;
		} else if (lowerTeam.contains("racing bulls")) {
			return PURPLE + teamName + RESET;
		} else if (lowerTeam.contains("alfa romeo")) {
			return BRIGHT_RED + teamName + RESET;
		} else if (lowerTeam.contains("sauber")) {
			return GREEN + teamName + RESET;
		} else if (lowerTeam.contains("audi")) {
			return WHITE + teamName + RESET;
		} else if (lowerTeam.contains("cadillac")) {
			return CYAN + teamName + RESET;
		} else {
			return teamName;
		}
	} // colourTeam close

	// printing top three drivers on small ascii podium
	public static void printPodium(Position first, Position second, Position third) {
		System.out.println();
//		System.out.println(BRIGHT_YELLOW + "                 DUDUDU" + RESET);
//		System.out.println();
		System.out.println("              " + BRIGHT_YELLOW + first.getDriverName() + RESET);
		System.out.println("              ┌──────────┐");
		System.out.println("              │    P1    │");
		System.out.println("              │          │");
		System.out.println(
				"   " + WHITE + second.getDriverName() + RESET + "        " + YELLOW + third.getDriverName() + RESET);
		System.out.println("   ┌──────────┐        ┌──────────┐");
		System.out.println("   │    P2    │        │    P3    │");
		System.out.println("   │          │        │          │");
		System.out.println("────────────────────────────────────");
		System.out.println();
	} // printPodium close

	
	// printing session menu with small f1 ascii art on the side
	public static void printSessionMenuWithArt(String sessionName) {
		String[] menu = {
				"========== " + sessionName + " ==========",
				"1. Show drivers in this session",
				"2. Show session positions/results",
				"3. Search driver lap times",
				"4. Show session info",
				"5. Back"
		};

		String[] art = {
				RED + "░▒▓████████▓▒░" + BLACK + "   ▒▓█▓▒░" + RESET,
				RED + "░▒▓█▓▒░       " + BLACK + "▒▓████▓▒░" + RESET,
				RED + "░▒▓█▓▒░       " + BLACK + "   ▒▓█▓▒░" + RESET,
				RED + "░▒▓██████▓▒░  " + BLACK + "   ▒▓█▓▒░" + RESET,
				RED + "░▒▓█▓▒░       " + BLACK + "   ▒▓█▓▒░" + RESET,
				RED + "░▒▓█▓▒░       " + BLACK + "   ▒▓█▓▒░" + RESET,
				RED + "░▒▓█▓▒░       " + BLACK + "   ▒▓█▓▒░" + RESET
		};

		System.out.println();

		// printing menu and art side by side
		for (int i = 0; i < art.length; i++) {
			String left = "";

			if (i < menu.length) {
				left = menu[i];
			}

			System.out.printf("%-45s %s%n", left, art[i]);
		}
	} // printSessionMenuWithArt close

	// this one may not work in some IDE consoles like Eclipse
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	} // clearScreen close

	// colouring race status
	public static String colourStatus(boolean cancelled) {
		if (cancelled) {
			return BRIGHT_RED + "Cancelled" + RESET;
		} else {
			return BRIGHT_GREEN + "Active" + RESET;
		}
	} // colourStatus close
}