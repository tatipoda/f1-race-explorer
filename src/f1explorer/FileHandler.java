package f1explorer;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class FileHandler {

	// file where we store user settings
	private final String SETTINGS_FILE = "settings.txt";

	// folder for api cache (offline mode)
	private final String CACHE_FOLDER = "cache";

	// constructor - making sure cache folder exists
	public FileHandler() {
		createCacheFolder();
	}

	// creating cache folder if it does not exist yet
	private void createCacheFolder() {

		File folder = new File(CACHE_FOLDER);

		// if folder not found - create it
		if (!folder.exists()) {
			folder.mkdir();
		}
	}

	// saving user settings to file
	public void saveSettings(Settings settings) {

		try {
			FileWriter writer = new FileWriter(SETTINGS_FILE);

			// writing settings line by line
			writer.write("defaultYear=" + settings.getDefaultYear() + "\n");
			writer.write("favouriteDriverNumber=" + settings.getFavouriteDriverNumber() + "\n");

			writer.close();

		} catch (Exception e) {
			System.out.println("Could not save settings.");
			System.out.println("Error: " + e.getMessage());
		}
	}

	// loading settings from file
	public Settings loadSettings() {

		// creating default settings first (in case file fails)
		Settings settings = new Settings();

		try {
			File file = new File(SETTINGS_FILE);

			// if settings file does not exist - create it with default values
			if (!file.exists()) {
				saveSettings(settings);
				return settings;
			}

			Scanner fileScanner = new Scanner(file);

			// reading file line by line
			while (fileScanner.hasNextLine()) {

				String line = fileScanner.nextLine();

				// parsing default year
				if (line.startsWith("defaultYear=")) {

					String year = line.replace("defaultYear=", "");

					// simple validation (only allowed years)
					if (year.matches("202[3-6]")) {
						settings.setDefaultYear(year);
					}
				}

				// parsing favourite driver number
				if (line.startsWith("favouriteDriverNumber=")) {

					String driverNumber = line.replace("favouriteDriverNumber=", "");

					// making sure it's numeric
					if (driverNumber.matches("[0-9]+")) {
						settings.setFavouriteDriverNumber(Integer.parseInt(driverNumber));
					}
				}
			}

			fileScanner.close();

		} catch (Exception e) {
			System.out.println("Could not load settings.");
			System.out.println("Default settings will be used.");
		}

		return settings;
	}

	// saving api response to cache file
	public void saveCache(String fileName, String data) {

		try {
			FileWriter writer = new FileWriter(CACHE_FOLDER + "/" + fileName);

			writer.write(data);

			writer.close();

		} catch (Exception e) {
			System.out.println("Could not save cache file.");
		}
	}

	// loading api response from cache (offline mode)
	public String loadCache(String fileName) {

		String data = "";

		try {
			File file = new File(CACHE_FOLDER + "/" + fileName);

			// if no cache file - return empty string
			if (!file.exists()) {
				return "";
			}

			Scanner fileScanner = new Scanner(file);

			// reading entire file content
			while (fileScanner.hasNextLine()) {
				data = data + fileScanner.nextLine();
			}

			fileScanner.close();

		} catch (Exception e) {
			System.out.println("Could not load cache file.");
		}

		return data;
	}
}

