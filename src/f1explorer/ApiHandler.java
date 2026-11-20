package f1explorer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiHandler {

		// base link for all OpenF1 api requests
		private final String BASE_URL = "https://api.openf1.org/v1/";

		// file handler used for cache/offline mode
		private FileHandler fileHandler;

		public ApiHandler() {
			fileHandler = new FileHandler();
		} // ApiHandler close


	// getting race meetings by year
	public ArrayList<Race> getRacesByYear(String year) {

		ArrayList<Race> races = new ArrayList<Race>();
		String apiUrl = BASE_URL + "meetings?year=" + year;
		String response = getApiResponse(apiUrl);

		// if api/cache returned nothing, return empty list
		if (response.isEmpty()) {
			return races;
		}

		try {
			JSONArray jsonArray = new JSONArray(response);

			// reading every race object from json array
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject raceObject = jsonArray.getJSONObject(i);

				int meetingKey = raceObject.getInt("meeting_key");
				String meetingName = raceObject.getString("meeting_name");
				String location = raceObject.getString("location");
				String countryName = raceObject.getString("country_name");
				String dateStart = raceObject.getString("date_start");
				boolean cancelled = raceObject.getBoolean("is_cancelled");

				// creating race object and adding it to list
				Race race = new Race(meetingKey, meetingName, location, countryName, dateStart, cancelled);
				races.add(race);
			}

		} catch (Exception e) {
			System.out.println("Could not read race data.");
			System.out.println("Error: " + e.getMessage());
		}

		return races;
	} // getRacesByYear close

	// getting all sessions for selected race
	public ArrayList<Session> getSessionsByMeetingKey(int meetingKey) {

		ArrayList<Session> sessions = new ArrayList<Session>();
		String apiUrl = BASE_URL + "sessions?meeting_key=" + meetingKey;
		String response = getApiResponse(apiUrl);

		if (response.isEmpty()) {
			return sessions;
		}

		try {
			JSONArray jsonArray = new JSONArray(response);

			// reading every session from api response
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject sessionObject = jsonArray.getJSONObject(i);

				int sessionKey = sessionObject.getInt("session_key");
				String sessionName = sessionObject.getString("session_name");
				String sessionType = sessionObject.getString("session_type");
				String dateStart = sessionObject.getString("date_start");
				String dateEnd = sessionObject.getString("date_end");

				Session session = new Session(sessionKey, sessionName, sessionType, dateStart, dateEnd);
				sessions.add(session);
			}

		} catch (Exception e) {
			System.out.println("Could not read session data.");
			System.out.println("Error: " + e.getMessage());
		}

		return sessions;
	} // getSessionsByMeetingKey close

	// getting drivers for selected session
	public ArrayList<Driver> getDriversBySessionKey(int sessionKey) {

		ArrayList<Driver> drivers = new ArrayList<Driver>();
		String apiUrl = BASE_URL + "drivers?session_key=" + sessionKey;
		String response = getApiResponse(apiUrl);

		if (response.isEmpty()) {
			return drivers;
		}

		try {
			JSONArray jsonArray = new JSONArray(response);

			// creating driver objects from json
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject driverObject = jsonArray.getJSONObject(i);
				Driver driver = createDriverFromJson(driverObject);
				drivers.add(driver);
			}

		} catch (Exception e) {
			System.out.println("Could not read driver data.");
			System.out.println("Error: " + e.getMessage());
		}

		return drivers;
	} // getDriversBySessionKey close

	// checking if driver is already in list by racing number
	private boolean driverAlreadyExists(
        ArrayList<Driver> drivers, Driver driver) {

    for (int i = 0; i < drivers.size(); i++) {

        if (drivers.get(i).getDriverNumber() == driver.getDriverNumber()
                && drivers.get(i).getFullName()
                        .equalsIgnoreCase(driver.getFullName())) {

            return true;
        }
    }

    return false;
}// driverAlreadyExists close
	
	// getting all drivers and removing duplicates
	public ArrayList<Driver> getAllDrivers() {

		ArrayList<Driver> drivers = new ArrayList<Driver>();
		String apiUrl = BASE_URL + "drivers";
		String response = getApiResponse(apiUrl);

		if (response.isEmpty()) {
			return drivers;
		}

		try {
			JSONArray jsonArray = new JSONArray(response);

			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject driverObject = jsonArray.getJSONObject(i);
				Driver driver = createDriverFromJson(driverObject);

				// OpenF1 can return same driver more than once, so we filter duplicates
				if (!driverAlreadyExists(drivers, driver)) {
					drivers.add(driver);
				}
			}

		} catch (Exception e) {
			System.out.println("Could not read driver data.");
			System.out.println("Error: " + e.getMessage());
		}

		return drivers;
	} // getAllDrivers close


	// getting lap times for one driver in one session
	public ArrayList<Lap> getLapsBySessionAndDriver(int sessionKey, int driverNumber) {

		ArrayList<Lap> laps = new ArrayList<Lap>();
		String apiUrl = BASE_URL + "laps?session_key=" + sessionKey + "&driver_number=" + driverNumber;
		String response = getApiResponse(apiUrl);

		if (response.isEmpty()) {
			return laps;
		}

		try {
			JSONArray jsonArray = new JSONArray(response);

			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject lapObject = jsonArray.getJSONObject(i);

				int lapNumber = lapObject.getInt("lap_number");
				double lapDuration = lapObject.optDouble("lap_duration", 0);

				// some laps can have missing duration, so only valid laps are added
				if (lapDuration > 0) {
					Lap lap = new Lap(lapNumber, driverNumber, lapDuration);
					laps.add(lap);
				}
			}

		} catch (Exception e) {
			System.out.println("Could not read lap data.");
			System.out.println("Error: " + e.getMessage());
		}

		return laps;
	} // getLapsBySessionAndDriver close

	// creating Driver object safely from json
	private Driver createDriverFromJson(JSONObject driverObject) {

		int driverNumber = driverObject.getInt("driver_number");
		String fullName = driverObject.optString("full_name", "Unknown driver");
		String teamName = driverObject.optString("team_name", "Unknown team");
		String countryCode = driverObject.optString("country_code", "N/A");

		// sometimes api gives null team name, so we fix it
		if (teamName == null || teamName.equals("null") || teamName.isEmpty()) {
			teamName = "Unknown team";
		}

		Driver driver = new Driver(driverNumber, fullName, teamName, countryCode);
		return driver;
	} // createDriverFromJson close
	
	// making safe file name for cache based on api url
	private String createCacheFileName(String apiUrl) {
		String fileName = apiUrl.replace(BASE_URL, "");
		fileName = fileName.replace("?", "_");
		fileName = fileName.replace("&", "_");
		fileName = fileName.replace("=", "-");
		fileName = fileName.replace("/", "_");

		return fileName + ".txt";
	} // createCacheFileName close
	
	// getting session positions/results
	public ArrayList<Position> getPositionsBySessionKey(int sessionKey) {

		ArrayList<Position> positions = new ArrayList<Position>();

		String apiUrl = BASE_URL + "position?session_key=" + sessionKey;
		String response = getApiResponse(apiUrl);

		if (response.isEmpty()) {
			return positions;
		}

		try {
			JSONArray jsonArray = new JSONArray(response);

			// loading drivers as well so positions can show names and teams
			ArrayList<Driver> drivers = getDriversBySessionKey(sessionKey);

			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject positionObject = jsonArray.getJSONObject(i);

				int driverNumber = positionObject.getInt("driver_number");
				int position = positionObject.getInt("position");

				// keeping latest position for every driver
				updatePositionList(positions, drivers, driverNumber, position);
			}

			sortPositions(positions);

		} catch (Exception e) {
			System.out.println("Could not read position data.");
			System.out.println("Error: " + e.getMessage());
		}

		return positions;
	} // getPositionsBySessionKey close

	// updating existing driver position or adding new one
	private void updatePositionList(ArrayList<Position> positions, ArrayList<Driver> drivers, int driverNumber, int position) {

		String driverName = "Driver " + driverNumber;
		String teamName = "Unknown team";

		// matching driver number with driver object to get name/team
		for (int i = 0; i < drivers.size(); i++) {
			if (drivers.get(i).getDriverNumber() == driverNumber) {
				driverName = drivers.get(i).getFullName();
				teamName = drivers.get(i).getTeamName();
			}
		}

		// if driver is already in list, replace with newer position
		for (int i = 0; i < positions.size(); i++) {
			if (positions.get(i).getDriverNumber() == driverNumber) {
				positions.set(i, new Position(driverNumber, position, driverName, teamName));
				return;
			}
		}

		positions.add(new Position(driverNumber, position, driverName, teamName));
	} // updatePositionList close

	// sorting positions from P1 down
	private void sortPositions(ArrayList<Position> positions) {

		// bubble sort because it is simple and easy to understand
		for (int i = 0; i < positions.size() - 1; i++) {

			for (int j = 0; j < positions.size() - i - 1; j++) {

				if (positions.get(j).getPosition() > positions.get(j + 1).getPosition()) {

					Position temp = positions.get(j);
					positions.set(j, positions.get(j + 1));
					positions.set(j + 1, temp);
				}
			}
		}
	} // sortPositions close
	
	// getting api response, or loading cache if api fails
	private String getApiResponse(String apiUrl) {

		String response = "";
		String cacheFileName = createCacheFileName(apiUrl);

		try {
			HttpURLConnection connection = (HttpURLConnection) URI.create(apiUrl).toURL().openConnection();	
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(10000);

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line = reader.readLine();

			// reading whole api response
			while (line != null) {
				response = response + line;
				line = reader.readLine();
			}

			reader.close();

			// saving successful response for offline use
			if (!response.isEmpty()) {
				fileHandler.saveCache(cacheFileName, response);
			}

		} catch (Exception e) {
   			System.out.println("Could not connect to API.");
  			System.out.println("Error: " + e.getMessage());
  			System.out.println("Trying to load saved cache...");

			// offline mode: try to load previous response
			response = fileHandler.loadCache(cacheFileName);

			if (response.isEmpty()) {
				System.out.println("No saved cache found.");
			} else {
				System.out.println("Loaded data from cache.");
			}
		}

		return response;
	} // getApiResponse close

}

