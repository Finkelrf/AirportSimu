import java.util.Calendar;
import java.util.Date;


public class Motor {
	Calendar c;
	int simStep; //in minutes
	
	public Motor() {
		this.c = Calendar.getInstance();
		c.set(2016,1,1,0,0,0);
		this.simStep = 1;
	}
	public void printSimulationCalendar(){
		System.out.println(c.getTime());
	}

	public void stepUp(){
		c.add(Calendar.MINUTE, this.simStep);
	}
	
	public int getTimePassed(Calendar calStart){
		//if(calStart.getTime())
	}



}


