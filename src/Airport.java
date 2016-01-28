import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class Airport {

	private static final int NUMBER_OF_GATES = 6;
	private static final int NUMBER_OF_TAXIWAYS = 2;
	
	private Track track = new Track();
	private Gate[] gates = new Gate[NUMBER_OF_GATES];
	private Taxiway taxiways[] = new Taxiway[NUMBER_OF_TAXIWAYS];
	private Tower tower;

	public Airport() {
		track.free();
		for (int i = 0; i < NUMBER_OF_GATES; i++) {
			gates[i] = new Gate();
			gates[i].enable();
			gates[i].setGateId(i);
		}

		for (int i = 0; i < NUMBER_OF_TAXIWAYS; i++) {
			taxiways[i] = new Taxiway();
			taxiways[i].enable();
		}
		taxiways[Taxiway.TAXIWAY_ARRIVE].setId(Taxiway.TAXIWAY_ARRIVE);
		taxiways[Taxiway.TAXIWAY_LEAVE].setId(Taxiway.TAXIWAY_LEAVE);
		tower = new Tower();
	}

	public Tower getTower() {
		return tower;
	}
	
	public Track getTrack(){
		return track;
	}

	public Gate[] getGates() {
		return gates;
	}

	public Taxiway[] getTaxiways() {
		return taxiways;
	}
	
	public int getFreeGateId() {
		for (int i = 0; i < getGates().length; i++) {
			if(getGates()[i].isAvailable()){
				return i;
			}
		}
		return -1;
	}

	
	
	
	

	

}
