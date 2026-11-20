package f1explorer;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// printing intro art before main menu
		UIHelper.printIntro();

		// waiting for user so intro screen does not disappear instantly
		System.out.print("Press Enter to start...");
		scanner.nextLine();
		
		// trying to clear console before showing menu
		UIHelper.clearScreen();

		// creating user interface object and starting the app
		UserInterface ui = new UserInterface(scanner);
		ui.start();

		scanner.close();
	} // main close
}