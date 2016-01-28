import java.util.Random;

class Taxiway{
		private TaxiwayState state;
		private int plane;
		private long delay;
		private int id;
		
		public static final int TAXIWAY_ARRIVE = 0;
		public static final int TAXIWAY_LEAVE= 1;
		
		public enum TaxiwayState {
			TAXIWAY_AVAILABLE, TAXIWAY_UNAVAILABLE, NEW_PLANE, OCCUPY
		}
		
		public void setId(int id){
			this.id = id;
		}
	
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
				System.out.println(this.plane+" is in the taxiway "+this.id);
				//define time in the taxyway
				a.getTower().getPlaneById(plane).isOnTaxiway();
				a.getTower().getPlaneById(plane).setLastStateDate();
				delay = randomDelay();
				state = TaxiwayState.OCCUPY;
				
				break;
			case OCCUPY:
				//check if plane still in the tawiway
				if(Motor.getTimePassed(a.getTower().getPlaneById(plane).getLastStateDate())>=delay){
					if(this.id == TAXIWAY_ARRIVE){
						//taxing time has passed
						int gateId = a.getFreeGateId();
						if(gateId != -1){
							a.getGates()[gateId].newPlane(plane);
							this.state = TaxiwayState.TAXIWAY_AVAILABLE;
							this.delay = -1;
							this.plane = -1;
						}else{
							//System.out.println(this.plane+" No free gates");
						}
					}else{
						if(a.getTrack().isAvailable()){
							System.out.println(this.plane+" is in the track");
							this.state = TaxiwayState.TAXIWAY_AVAILABLE;
							a.getTower().addToTakeOffList(this.plane);						
							this.delay = -1;
							this.plane = -1;
						}
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