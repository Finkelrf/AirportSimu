

class Gate{
		private static final long DISEMBARKATION_TIME = 10;
		private static final long PLANE_PREPARATION_TIME = 30;
		private static final long EMBARKATION_TIME = 20;
		private GateState state;
		private int plane;
		private long delay;
		private int gateId;

		public enum GateState {
			GATE_AVAILABLE, GATE_UNAVAILABLE, NEW_PLANE, DISEMBARKATION_PASSENGERS, PREPARING_PLANE, EMBARKATION, WAITING_FOR_TAXIWAY
		}

		public Gate() {
			this.state = GateState.GATE_AVAILABLE;
			this.delay = -1;
			this.gateId = -1;
			this.plane = -1;
		}
		
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
			System.out.println("gate "+gateId+" plane "+this.plane);
			switch (this.state) {
			case NEW_PLANE:
				System.out.println(this.plane +" arrived to gate "+this.gateId);
				this.state = GateState.DISEMBARKATION_PASSENGERS;
				this.delay = DISEMBARKATION_TIME;
				a.getTower().getPlaneById(plane).isAtGate();
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
				if((Motor.getTimePassed(a.getTower().getPlaneById(plane).getLastStateDate()))>=delay){
					System.out.println(this.plane+" embarking");
					this.state = GateState.WAITING_FOR_TAXIWAY;
					this.delay = -1;
					a.getTower().getPlaneById(plane).setLastStateDate();
				}
				break;
			case WAITING_FOR_TAXIWAY:
				if(a.getTaxiways()[Taxiway.TAXIWAY_LEAVE].isAvailable()){
					System.out.println(this.plane+" got taxiway");
					a.getTaxiways()[Taxiway.TAXIWAY_LEAVE].newPlaneId(this.plane);
					this.plane = -1;
					this.state = GateState.GATE_AVAILABLE;
				}
				break;
			case GATE_AVAILABLE:
			case GATE_UNAVAILABLE:
				break;


			default:
				break;
			}
		}
	}