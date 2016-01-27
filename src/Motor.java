import java.util.Calendar;
import java.util.Date;


public class Motor {
	private static Calendar c = Calendar.getInstance();
	private static int simStep; //in minutes
	
	public static void setStartDate() {
		c.set(2016,1,1,0,0,0);
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
	
	//return how long has passed in minutes
	public static long getTimePassed(Date olderDate){
		return (c.getTimeInMillis() - olderDate.getTime())/(1000*60);
	}

}


