package f1explorer;

public class Lap {

	// lap number inside session
	private int lapNumber;

	// driver racing number for this lap
	private int driverNumber;

	// lap time in seconds
	private double lapDuration;

	// constructor to create lap object from api data
	public Lap(int lapNumber, int driverNumber, double lapDuration) {
		this.lapNumber = lapNumber;
		this.driverNumber = driverNumber;
		this.lapDuration = lapDuration;
	} // Lap close

	// returning lap number
	public int getLapNumber() {
		return lapNumber;
	} // getLapNumber close

	// returning driver racing number
	public int getDriverNumber() {
		return driverNumber;
	} // getDriverNumber close

	// returning lap duration in seconds
	public double getLapDuration() {
		return lapDuration;
	} // getLapDuration close

	// printing one lap in readable format
	public void printLap() {
		System.out.println("Lap " + lapNumber + " - " + FormatHelper.formatLapTime(lapDuration));
	} // printLap close
}
