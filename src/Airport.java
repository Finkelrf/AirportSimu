import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Airport {

	private static final int NUMBER_OF_GATES = 6;
	private static final int NUMBER_OF_TAXIWAYS = 2;
	public static final int TAXIWAY_ARRIVE = 0;
	public static final int TAXIWAY_LEAVE= 1;

	public enum TrackState {
		TRACK_AVAILABLE, TRACK_UNAVAILABLE,WAITING_PLANE_LAND,PLANE_LANDING, PLANE_LANDED,
	}

	public enum GateState {
		GATE_AVAILABLE, GATE_UNAVAILABLE, NEW_PLANE, DISEMBARKATION_PASSENGERS, PREPARING_PLANE, EMBARKATION
	}

	public enum TaxiwayState {
		TAXIWAY_AVAILABLE, TAXIWAY_UNAVAILABLE, NEW_PLANE, OCCUPY
	}

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
		tower = new Tower();
	}

	public int fligthListSize() {
		return tower.planeList.size();
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

	class Tower {

		private ArrayList<Plane> planeList;
		boolean planeWaitingToLand;

		public Tower() {
			planeList = new ArrayList<Plane>();
			planeWaitingToLand = false;
		}
		
		public boolean isPlaneWaitingToLand(){
			return planeWaitingToLand;
		}

		// Give a number to a arriving plane
		public int registerNewAproache() {
			this.planeList.add(new Plane(Motor.getNowDate()));
			planeWaitingToLand = true;
			getTrack().setDelayToLand();
			int id = planeList.get(planeList.size() - 1).setId(planeList.size()-1);
			return id;
		}

		public int fligthListSize() {
			return planeList.size();
		}
		
		public int sendPermissionToLand(){
			for (int i = 0; i < planeList.size(); i++) {
				if(planeList.get(i).getState() == Plane.PLANE_STATE.WAITING_TRACK){
					planeList.get(i).permissionToLand();
					return planeList.get(i).getId();
				}
			}
			return -1;
		}

		public Plane getPlaneById(int planeId) {
			// TODO Auto-generated method stub
			return planeList.get(planeId);
		}

	}
	
	class Track{
		private static final int LAND_TIME = 2;
		TrackState state = TrackState.TRACK_UNAVAILABLE;	
		int delayToLand = 0;
		int plane = -1;
		
		
		public int getDelayToLand() {
			return delayToLand;
		}

		private int randomDelayToLand(){
			return new Random().nextInt()%3;
		}
		
		public void setDelayToLand() {
			this.delayToLand = randomDelayToLand();
		}

		public void free() {
			this.state = TrackState.TRACK_AVAILABLE;
		}

		public void occupy() {
			this.state = TrackState.WAITING_PLANE_LAND;
		}

		public boolean isAvailable() {
			return this.state == TrackState.TRACK_AVAILABLE;
		}
		
		public void handler(Airport a){
			switch (state) {
			case WAITING_PLANE_LAND:
				if(plane != -1){
					if(Motor.getTimePassed(a.getTower().getPlaneById(plane).getLastStateDate())>=delayToLand){
						a.getTower().getPlaneById(plane).land();
						System.out.println(a.getTower().getPlaneById(plane).getId()+" landing");
						delayToLand = LAND_TIME;
						state = TrackState.PLANE_LANDING;
						a.getTower().getPlaneById(plane).setLastStateDate();

					}
				}
				break;
			case PLANE_LANDING:
				if(Motor.getTimePassed(a.getTower().getPlaneById(plane).getLastStateDate())>delayToLand){
					a.getTower().getPlaneById(plane).landed();
					System.out.println(a.getTower().getPlaneById(plane).getId()+" sucessfully landed");
					state = TrackState.PLANE_LANDED;
					
					//plane landed, now taxing
				}				
				break;
			case PLANE_LANDED:
				//go to taxiway
				if(a.getTaxiways()[TAXIWAY_ARRIVE].isAvailable()){
					state = TrackState.TRACK_AVAILABLE;
					a.getTaxiways()[TAXIWAY_ARRIVE].newPlaneId(plane);
					delayToLand = -1;
					plane = -1;
				}
				break;
				
			case TRACK_AVAILABLE:
			case TRACK_UNAVAILABLE:
				break;

			default:
				break;
			}
		}

		public void setPlane(int planeId) {
			this.plane = planeId;			
		}

	}
	
	class Gate{
		private static final long DISEMBARKATION_TIME = 10;
		private static final long PLANE_PREPARATION_TIME = 30;
		private static final long EMBARKATION_TIME = 20;
		GateState state;
		int plane;
		long delay;
		int gateId;
		
		public void enable() {
			state = GateState.GATE_AVAILABLE;
		}

		public void setGateId(int id) {
			this.gateId = id;
		}

		public void disable() {
			state = GateState.GATE_UNAVAILABLE;
		}

		public boolean isAvailable() {
			return state == GateState.GATE_AVAILABLE;
		}

		public void newPlane(int plane) {
			this.plane = plane;
			state = GateState.NEW_PLANE;
		}
		public void handler(Airport a){
			switch (this.state) {
			case NEW_PLANE:
				System.out.println(this.plane +" arrived to gate "+this.gateId);
				this.state = GateState.DISEMBARKATION_PASSENGERS;
				this.delay = DISEMBARKATION_TIME;
				a.getTower().getPlaneById(plane).setLastStateDate();
				break;
			case DISEMBARKATION_PASSENGERS:
				if((Motor.getTimePassed(a.getTower().getPlaneById(plane).getLastStateDate()))>=delay){
					System.out.println(this.plane+" Disembarkation");
					this.state = GateState.PREPARING_PLANE;
					this.delay = PLANE_PREPARATION_TIME;
					a.getTower().getPlaneById(plane).setLastStateDate();
				}
				break;
			case PREPARING_PLANE:
				if((Motor.getTimePassed(a.getTower().getPlaneById(plane).getLastStateDate()))>=delay){
					System.out.println(this.plane+" Preparation");
					this.state = GateState.EMBARKATION;
					this.delay = EMBARKATION_TIME;
					a.getTower().getPlaneById(plane).setLastStateDate();
					
					//TODO get a new flight number from tower
				}
				break;
			case EMBARKATION:
				this.state = GateState.GATE_AVAILABLE;
				break;
			case GATE_AVAILABLE:
			case GATE_UNAVAILABLE:
				break;


			default:
				break;
			}
		}
	}
	
	class Taxiway{
		TaxiwayState state;
		int plane;
		long delay;
	
		public void enable() {
			state = TaxiwayState.TAXIWAY_AVAILABLE;
		}

		public void newPlaneId(int plane) {
			this.plane = plane;	
			state = TaxiwayState.NEW_PLANE;
		}

		public void disable() {
			state = TaxiwayState.TAXIWAY_UNAVAILABLE;
		}

		public boolean isAvailable() {
			return state == TaxiwayState.TAXIWAY_AVAILABLE;
		}
		
		private int randomDelay(){
			return (new Random().nextInt()%4)+2;
		}

		public void handler(Airport a) {
			switch (state) {
			case NEW_PLANE:
				System.out.println(this.plane+" in the arrive taxiway ");
				//define time in the taxyway
				a.getTower().getPlaneById(plane).setLastStateDate();
				delay = randomDelay();
				state = TaxiwayState.OCCUPY;
				break;
			case OCCUPY:
				//check if plane still in the tawiway
				if(Motor.getTimePassed(a.getTower().getPlaneById(plane).getLastStateDate())>=delay){
					//taxing time has passed
					int gateId = a.getFreeGateId();
					if(gateId != -1){
						a.getGates()[gateId].newPlane(plane);
						this.state = TaxiwayState.TAXIWAY_AVAILABLE;
					}else{
						System.out.println(this.plane+" No free gates");
					}
					
				}
				break;
			case TAXIWAY_AVAILABLE:
			case TAXIWAY_UNAVAILABLE:
				break;

			default:
				break;
			}			
		}
	}

	

}
