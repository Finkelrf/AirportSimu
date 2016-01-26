import java.util.Calendar;
import java.util.Date;


public class Plane {
	int id;
	STATE state;
	Calendar calStateStar;
	enum STATE{
		NOTIFY_TOWER,
		WAITING_TRACK,
		APROACHE,
		LANDING,
		TAXING,
		NOTIFY_ARRIVE,
		WAITING_PASSANGERS_LEAVE
	}
	
	public Plane(Calendar c) {
		this.id = 0;
		this.state = STATE.NOTIFY_TOWER;
		this.calStateStar = Calendar.getInstance();
		this.calStateStar.setTime((Date) c.getTime().clone());
	}
	
	public void setId(int id){
		this.id = id;
		this.state = STATE.WAITING_TRACK;
	}
	
	public boolean permissionToLand(){
		if(this.state == STATE.WAITING_TRACK){
			this.state = STATE.APROACHE;
			return true;
		}else{
			return false;
		}
	}
	
	public void printSimulationCalendar(){
		System.out.println(this.calStateStar.getTime());
	}
	
}
