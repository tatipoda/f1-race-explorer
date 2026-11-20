package f1explorer;

public class Settings {

	// saved default year for race browsing
	private String defaultYear;

	// saved favourite driver racing number
	private int favouriteDriverNumber;

	// default settings used if settings file does not exist yet
	public Settings() {
		defaultYear = "2024";
		favouriteDriverNumber = 1;
	} // Settings close

	// constructor used if we want to create settings with custom values
	public Settings(String defaultYear, int favouriteDriverNumber) {
		this.defaultYear = defaultYear;
		this.favouriteDriverNumber = favouriteDriverNumber;
	} // Settings close

	// returning saved default year
	public String getDefaultYear() {
		return defaultYear;
	} // getDefaultYear close

	// updating default year
	public void setDefaultYear(String defaultYear) {
		this.defaultYear = defaultYear;
	} // setDefaultYear close

	// returning saved favourite driver number
	public int getFavouriteDriverNumber() {
		return favouriteDriverNumber;
	} // getFavouriteDriverNumber close

	// updating favourite driver number
	public void setFavouriteDriverNumber(int favouriteDriverNumber) {
		this.favouriteDriverNumber = favouriteDriverNumber;
	} // setFavouriteDriverNumber close
}
