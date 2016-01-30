import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Measure {
	private static Retard ret = new Retard();
	private static FreQCtrl freq = new FreQCtrl();
	public static Retard getRet() {
		return ret;
	}
	public static FreQCtrl getFreq() {
		return freq;
	}

}

class FreQCtrl{
	private ArrayList<Date> dateList = new ArrayList<>();
	private ArrayList<Integer> dayOfWeekList = new ArrayList<>();
	private ArrayList<TravelType> travelType = new ArrayList<>();

	private enum TravelType{
		ARRIVE,
		DEPART,
	}
	
	public void addArrival(){
		dateList.add(Motor.getNowDate());
		travelType.add(TravelType.ARRIVE);
		dayOfWeekList.add(Motor.getDayOfWeek());
		System.out.println(dateList.get(dateList.size()-1).getTime()+" day of week "+dayOfWeekList.get(dayOfWeekList.size()-1));
	}
	
	public void addDepart(){
		dateList.add(Motor.getNowDate());
		dayOfWeekList.add(Motor.getDayOfWeek());
		travelType.add(TravelType.DEPART);
	}
	
	public void printFreqLog() throws IOException{
		CsvManager.setFreqFile();
		
		//write weekday freq		
		int weekdayArrive[] = {0,0,0,0,0,0,0};
		int weekdayDepart[] = {0,0,0,0,0,0,0};
		
		for (int i = 0; i < dateList.size(); i++) {
			if(travelType.get(i) == TravelType.ARRIVE){
				weekdayArrive[dayOfWeekList.get(i)]++;
			}else{
				weekdayDepart[dayOfWeekList.get(i)]++;
			}
		}
		//write average by week day in file
				for (int i = 0; i < weekdayArrive.length; i++) {
					switch (i) {
					case 0:
						CsvManager.registerData(new String []{"Arrive Sunday:",""+weekdayArrive[i]});
						System.out.println("Arrive Sunday: "+weekdayArrive[i]);
						break;
					case 1:
						CsvManager.registerData(new String []{"Arrive Monday:",""+weekdayArrive[i]});
						System.out.println("Arrive Monday: "+weekdayArrive[i]);
						break;
					case 2:
						CsvManager.registerData(new String []{"Arrive Tuesday:",""+weekdayArrive[i]});
						System.out.println("Arrive Tuesday: "+weekdayArrive[i]);
						break;
					case 3:
						CsvManager.registerData(new String []{"Arrive Wednesday:",""+weekdayArrive[i]});
						System.out.println("Arrive Wednesday: "+weekdayArrive[i]);
						break;
					case 4:
						CsvManager.registerData(new String []{"Arrive Thursday:",""+weekdayArrive[i]});
						System.out.println("Arrive Thursday: "+weekdayArrive[i]);
						break;
					case 5:
						CsvManager.registerData(new String []{"Arrive Friday:",""+weekdayArrive[i]});
						System.out.println("Arrive Friday: "+weekdayArrive[i]);
						break;
					case 6:
						CsvManager.registerData(new String []{"Arrive Saturday:",""+weekdayArrive[i]});
						System.out.println("Arrive Saturday: "+weekdayArrive[i]);
						break;
					default:
						break;
					}
				}
				System.out.println(" ");
				
				//write average by week day in file
				for (int i = 0; i < weekdayDepart.length; i++) {
					switch (i) {
					case 0:
						CsvManager.registerData(new String []{"Depart Sunday:",""+weekdayDepart[i]});
						System.out.println("Depart Sunday: "+weekdayDepart[i]);
						break;
					case 1:
						CsvManager.registerData(new String []{"Depart Monday:",""+weekdayDepart[i]});
						System.out.println("Depart Monday: "+weekdayDepart[i]);
						break;
					case 2:
						CsvManager.registerData(new String []{"Depart Tuesday:",""+weekdayDepart[i]});
						System.out.println("Depart Tuesday: "+weekdayDepart[i]);
						break;
					case 3:
						CsvManager.registerData(new String []{"Depart Wednesday:",""+weekdayDepart[i]});
						System.out.println("Depart Wednesday: "+weekdayDepart[i]);
						break;
					case 4:
						CsvManager.registerData(new String []{"Depart Thursday:",""+weekdayDepart[i]});
						System.out.println("Depart Thursday: "+weekdayDepart[i]);
						break;
					case 5:
						CsvManager.registerData(new String []{"Depart Friday:",""+weekdayDepart[i]});
						System.out.println("Depart Friday: "+weekdayDepart[i]);
						break;
					case 6:
						CsvManager.registerData(new String []{"Depart Saturday:",""+weekdayDepart[i]});
						System.out.println("Depart Saturday: "+weekdayDepart[i]);
						break;
					default:
						break;
					}
				}
				
				CsvManager.registerData(new String []{"Average arrives per hour"});
				int hourAvgArrive[] = new int[24];
				int hourAvgDepart[] = new int[24];
				//print average arrives per hour
				for (int i = 0; i < dateList.size(); i++) {
					if(travelType.get(i) == TravelType.ARRIVE)
						hourAvgArrive[dateList.get(i).getHours()]++;
					else
						hourAvgDepart[dateList.get(i).getHours()]++;
				}
				
				for (int i = 0; i < hourAvgArrive.length; i++) {
					if(i<22 && i>=7){
						System.out.println("Arrive "+i+"h "+hourAvgArrive[i]+" planes");
						CsvManager.registerData(new String []{""+i,""+hourAvgArrive[i]});
					}
				}
				
				
				CsvManager.registerData(new String []{"Average departs per hour"});
				for (int i = 0; i < hourAvgDepart.length; i++) {
					if(i<22 && i>=7){
						System.out.println("Depart "+i+"h "+hourAvgDepart[i]+" planes");
						CsvManager.registerData(new String []{""+i,""+hourAvgDepart[i]});
					}
				}
				
				CsvManager.registerData(new String []{"Average arrives per day"});
				//print flights per day
				int dayAvgArrive[] = new int[31];
				int dayAvgDepart[] = new int[31];
				//print average arrives per hour
				for (int i = 0; i < dateList.size(); i++) {
					Calendar c = Calendar.getInstance();
					c.setTime(dateList.get(i));
					if(travelType.get(i) == TravelType.ARRIVE)
						dayAvgArrive[c.get(Calendar.DAY_OF_MONTH)-1]++;
					else
						dayAvgDepart[c.get(Calendar.DAY_OF_MONTH)-1]++;
				}
				
				for (int i = 0; i < dayAvgArrive.length; i++) {
					System.out.println("Arrive "+(i+1)+" day "+dayAvgArrive[i]+" planes");
					CsvManager.registerData(new String []{""+(i+1),""+dayAvgArrive[i]});
				}
				CsvManager.registerData(new String []{"Average departs per day"});
				for (int i = 0; i < dayAvgDepart.length; i++) {
					System.out.println("Depart "+(i+1)+" day "+dayAvgDepart[i]+" planes");
					CsvManager.registerData(new String []{""+(i+1),""+dayAvgDepart[i]});
				}
				
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

	public void printAll() throws IOException {
		if(flightsLand !=0)
			System.out.println("Landing retards " + flightsLand + " planes "
				+ timeLand / flightsLand + " minutes/plane ");
			CsvManager.registerData(new String[] {"Landing retards ",""+flightsLand,"planes",""+timeLand / flightsLand,"minutes/plane"});
		if(flightsTakeOff!=0)
			System.out.println("TakeOff retards " + flightsTakeOff + " planes "
				+ timeTakeOff / flightsTakeOff + " minutes/plane ");
		CsvManager.registerData(new String[] {"TakeOff retards ",""+flightsTakeOff,"planes",""+timeTakeOff / flightsTakeOff,"minutes/plane"});
	}

}
