import java.beans.FeatureDescriptor;
import java.util.Calendar;
import java.util.Date;


public class Motor {
	private static Calendar c = Calendar.getInstance();
	private static int dayOfWeek = 0;
	private static int simStep; //in minutes
	
	public static void setStartDate() {
		c.set(2016,Calendar.FEBRUARY,1,0,0,0);
		simStep = 1;
	}
	public Calendar getSimulationCalendar(){
		return c;
	}
	
	public static Date getNowDate(){
		return c.getTime();
	}

	public static void stepUp(){
		c.add(Calendar.MINUTE, simStep);
		if(c.get(Calendar.HOUR_OF_DAY)==0){
			if(dayOfWeek==6)
				dayOfWeek = 0;
			else
				dayOfWeek++;
		}
			
		System.out.println(c.getTime()+" dia da semana "+dayOfWeek);
	}
	public static boolean isWeekend(){
		int weekDay = dayOfWeek;
		if(weekDay>0 && weekDay<6)
			return false;
		else
			return true;
	}
	
	//return how long has passed in minutes
	public static long getTimePassed(Date olderDate){
		long delay = (c.getTimeInMillis() - olderDate.getTime())/(1000*60);
		return (delay);
	}
	public static Calendar getNowCal() {
		return c;
	}
	public static Integer getDayOfWeek() {
		return dayOfWeek;
	}
	public static boolean airportIsOpen() {
		if(Motor.getNowDate().getHours()<22 && Motor.getNowDate().getHours()>=7)
			return true;
		else 
			return false;
	}
	

}


