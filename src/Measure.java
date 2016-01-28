public class Measure {
	static Retard ret = new Retard();

	public static Retard getRet() {
		return ret;
	}

}

class Retard {
	private long timeLand = 0;
	private long flightsLand = 0;

	private long timeTakeOff = 0;
	private long flightsTakeOff = 0;

	public static final long RETARD_TIME = 5;

	public void addLand(long time) {
		flightsLand++;
		timeLand = timeLand + time;
	}

	public void addTakeOff(long time) {
		flightsTakeOff++;
		timeTakeOff = timeTakeOff + time;
	}

	public void printAll() {
		if(flightsLand !=0)
			System.out.println("Landing retards " + flightsLand + " planes "
				+ timeLand / flightsLand + " minutes/plane ");
		if(flightsTakeOff!=0)
			System.out.println("Landing retards " + flightsTakeOff + " planes "
				+ timeTakeOff / flightsTakeOff + " minutes/plane ");
	}

}
