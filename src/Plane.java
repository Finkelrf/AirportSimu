
import java.util.Calendar;
import java.util.Date;


public class Plane {
	private int id;
	private STATE state;
	private Date date;
	
	enum STATE{
		NOTIFY_TOWER,
		WAITING_TRACK,
		APROACHE,
		LANDING,
		TAXING,
		NOTIFY_ARRIVE,
		WAITING_PASSANGERS_LEAVE
	}
	
	public Plane(Date date) {
		this.id = 0;
		this.state = STATE.NOTIFY_TOWER;
		this.date = date;
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
	public Date getLastStateDate(){
		return this.date;
	}

	
}
