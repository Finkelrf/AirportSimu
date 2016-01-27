<<<<<<< HEAD
=======

import java.util.Calendar;
>>>>>>> 479022d5685ad3d2d2e2bc46c9b9be815ed228be
import java.util.Date;


public class Plane {
	private int id;
	private PLANE_STATE state;
	private Date date;
	
	public enum PLANE_STATE{
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
		this.state = PLANE_STATE.NOTIFY_TOWER;
		this.date = date;
	}
	
	public int setId(int id){
		this.id = id;
		this.state = PLANE_STATE.WAITING_TRACK;
		return this.id;
	}
	public int getId(){
		return id;
	}
	
	public boolean permissionToLand(){
		if(this.state == PLANE_STATE.WAITING_TRACK){
			this.state = PLANE_STATE.APROACHE;
			return true;
		}else{
			return false;
		}
	}
	public Date getLastStateDate(){
		return this.date;
	}
	
	public PLANE_STATE getState(){
		return this.state;
	}

	public void landed() {
		state = PLANE_STATE.TAXING;		
	}

	public void land() {
		state = PLANE_STATE.LANDING;		
	}

	public void setLastStateDate() {
		date = Motor.getNowDate();
		
	}

	
	
}
