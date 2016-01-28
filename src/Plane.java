import java.util.Date;


public class Plane {
	private int id;
	private PLANE_STATE state;
	private Date date;
	
	public enum PLANE_STATE{
		AIR,
		GATE,
		TRACK,
		TAXIWAY,
	}
	
	public Plane(Date date) {
		this.id = 0;
		this.date = date;
		this.state = PLANE_STATE.AIR;
	}
	
	public int setId(int id){
		this.id = id;
		return this.id;
	}
	public int getId(){
		return id;
	}
	
	public Date getLastStateDate(){
		return this.date;
	}
	
	public PLANE_STATE getState(){
		return this.state;
	}


	public void setLastStateDate() {
		date = Motor.getNowDate();
		
	}
	
	public void isOnAir(){
		this.state = PLANE_STATE.AIR;
	}
	
	public void isOnTaxiway(){
		this.state = PLANE_STATE.TAXIWAY;
	}

	public void isAtGate(){
		this.state = PLANE_STATE.GATE;
	}
	
	public void isOnTrack(){
		this.state = PLANE_STATE.TRACK;
	}


	
	
}
