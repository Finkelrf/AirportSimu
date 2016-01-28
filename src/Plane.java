import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class Plane {
	private int id;
	private PLANE_STATE state;
	private Date date;
	private ArrayList<String> log;
	
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
		this.log = new ArrayList<String>();
	}
	
	public void addLog(String s){
		log.add(s);
	}
	
	public void writeLog(){
		String [] tempS = new String[log.size()];
		tempS = log.toArray(tempS);
		try {
			CsvManager.registerData(tempS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error to write in file");
		}
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

	public long getWaitingTime(){
		long time = Motor.getTimePassed(this.getLastStateDate());
		return time;
	}
	
	
}
