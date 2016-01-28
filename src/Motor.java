import java.util.Calendar;
import java.util.Date;


public class Motor {
	private static Calendar c = Calendar.getInstance();
	private static int simStep; //in minutes
	
	public static void setStartDate() {
		c.set(2016,1,1,8,0,0);
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
	}
	public static boolean isWeekend(){
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		if(weekDay>1 && weekDay>7)
			return true;
		else
			return false;
	}
	
	//return how long has passed in minutes
	public static long getTimePassed(Date olderDate){
		long delay = (c.getTimeInMillis() - olderDate.getTime())/(1000*60);
		return (delay);
	}
	

}


