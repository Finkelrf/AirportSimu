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
		GATE_AVAILABLE, GATE_UNAVAILABLE
	}

	public enum TaxiwayState {
		TAXIWAY_AVAILABLE, TAXIWAY_UNAVAILABLE, NEW_PLANE
	}

	private Track track = new Track();
	private Gate[] gates = new Gate[NUMBER_OF_GATES];
	private Taxiway taxiways[] = new Taxiway[NUMBER_OF_TAXIWAYS];
	private Tour tour;

	public Airport() {
		track.free();
		for (int i = 0; i < NUMBER_OF_GATES; i++) {
			gates[i] = new Gate();
			gates[i].enable();
		}

		for (int i = 0; i < NUMBER_OF_TAXIWAYS; i++) {
			taxiways[i] = new Taxiway();
			taxiways[i].enable();
		}
		tour = new Tour();
	}

	public int fligthListSize() {
		return tour.planeList.size();
	}

	public Tour getTour() {
		return tour;
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

	class Tour {

		private ArrayList<Plane> planeList;
		boolean planeWaitingToLand;

		public Tour() {
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
					if(Motor.getTimePassed(a.getTour().getPlaneById(plane).getLastStateDate())>delayToLand){
						a.getTour().getPlaneById(plane).land();
						System.out.println("Plane "+a.getTour().getPlaneById(plane).getId()+" landing");
						delayToLand = LAND_TIME;
						state = TrackState.PLANE_LANDING;
						a.getTour().getPlaneById(plane).setLastStateDate();

					}
				}
				break;
			case PLANE_LANDING:
				if(Motor.getTimePassed(a.getTour().getPlaneById(plane).getLastStateDate())>delayToLand){
					a.getTour().getPlaneById(plane).landed();
					System.out.println("Plane "+a.getTour().getPlaneById(plane).getId()+" sucessfully landed");
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
		GateState state;
		
		public void enable() {
			state = GateState.GATE_AVAILABLE;
		}

		public void disable() {
			state = GateState.GATE_UNAVAILABLE;
		}

		public boolean isAvailable() {
			return state == GateState.GATE_AVAILABLE;
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
				System.out.println("new plane in the arrive taxiway ");
				//define time in the taxyway
				a.getTour().getPlaneById(plane).setLastStateDate();
				delay = randomDelay();
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
