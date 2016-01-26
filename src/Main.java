
public class Main {
	public static void main(String[] args) {
		Motor m = new Motor();
		Plane p = new Plane(m.c);
		
		for (int i = 0; i < 100; i++) {
			//m.printSimulationCalendar();
			m.stepUp();
			p.printSimulationCalendar();
		}
		
	}
}
