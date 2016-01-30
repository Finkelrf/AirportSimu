import java.util.Date;
import java.util.Random;

class Track{
		private static final int LAND_TIME = 2;
		private TrackState state = TrackState.TRACK_UNAVAILABLE;	
		private int delay = 0;
		private int plane = -1;
		
		public enum TrackState {
			TRACK_AVAILABLE, TRACK_UNAVAILABLE,WAITING_PLANE_LAND,PLANE_LANDING, PLANE_LANDED, TAKING_OFF, PLANE_TOOK_OFF,
		}
		

		public Track() {
			this.state = TrackState.TRACK_AVAILABLE;
			this.delay = -1;
			this.plane = -1;
		}
		
		public int getDelayToLand() {
			return delay;
		}

		private int randomDelayToLand(){
			return (new Random().nextInt()%3 )+ 2;
		}
		
		public void setDelayToLand() {
			this.delay = randomDelayToLand();
		}

		public void free() {
			this.state = TrackState.TRACK_AVAILABLE;
		}


		public boolean isAvailable() {
			return this.state == TrackState.TRACK_AVAILABLE;
		}
		
		public void handler(Airport a){
			System.out.println("track plane "+this.plane);
			switch (state) {
			case WAITING_PLANE_LAND:
				if(plane != -1){
					if(Motor.getTimePassed(a.getTower().getPlaneById(plane).getLastStateDate())>=delay){
						System.out.println(a.getTower().getPlaneById(plane).getId()+" landing");
						delay = LAND_TIME;
						state = TrackState.PLANE_LANDING;
						//save log
						Measure.getFreq().addArrival();
						a.getTower().getPlaneById(plane).addLog("Approach pista: "+Motor.getTimePassed(a.getTower().getPlaneById(plane).getLastStateDate()));
						a.getTower().getPlaneById(plane).setLastStateDate();
						

					}
				}
				break;
			case PLANE_LANDING:
				if(Motor.getTimePassed(a.getTower().getPlaneById(plane).getLastStateDate())>delay){
					a.getTower().getPlaneById(plane).isOnTrack();
					System.out.println(a.getTower().getPlaneById(plane).getId()+" sucessfully landed");
					state = TrackState.PLANE_LANDED;
					
					//plane landed, now taxing
				}				
				break;
			case PLANE_LANDED:
				//go to taxiway
				if(a.getTaxiways()[Taxiway.TAXIWAY_ARRIVE].isAvailable()){
					System.out.println(this.plane+" went to TW1");
					state = TrackState.TRACK_AVAILABLE;
					a.getTaxiways()[Taxiway.TAXIWAY_ARRIVE].newPlaneId(plane);
					this.delay = -1;
					this.plane = -1;
					
				}
				break;
			case TAKING_OFF: 
				Date d= a.getTower().getPlaneById(plane).getLastStateDate();
				if(Motor.getTimePassed(d)>delay && Motor.airportIsOpen()){
					System.out.println(this.plane+" sucessfully take off");
					state = TrackState.TRACK_AVAILABLE;
					Measure.getFreq().addDepart();
					//notify tower 
					a.getTower().notifyTakeOff(a.getTower().getPlaneById(plane));
					this.delay = -1;
					this.plane = -1;
				}
				break;
				
			case TRACK_AVAILABLE:
				if(!a.getTower().isLandListEmpty() || !a.getTower().isTakeOffListEmpty()){
					//get plane waiting longer
					int toLand = a.getTower().getFirstLandList();
					int toTakeOff = a.getTower().getFirstTakeOffList();
					if(a.getFreeGateId() == -1){
						//no free gates
						if(toTakeOff != -1){
							//track is given to take off plane
							this.plane = toTakeOff;
							this.state = TrackState.TAKING_OFF;
							//save log
							a.getTower().getPlaneById(plane).addLog("Espera takeoff: "+Motor.getTimePassed(a.getTower().getPlaneById(toTakeOff).getLastStateDate()));
							if(a.getTower().getPlaneById(this.plane).getWaitingTime()>=Retard.RETARD_TIME)
								Measure.getRet().addTakeOff(a.getTower().getPlaneById(this.plane).getWaitingTime());
							a.getTower().rmFirstFromTakeOffList();
							a.getTrack().setDelayToTakeOff();	
							a.getTower().getPlaneById(toTakeOff).setLastStateDate();
						}
					}else{
						//free gates
						
						if(toLand != -1 ){
							//track is given to land plane
							this.state = TrackState.WAITING_PLANE_LAND;
							//check if plane spend too much time waiting to take off
							a.getTrack().setDelayToLand();
							int planeId = a.getTower().sendPermissionToLand();
							a.getTrack().setPlane(planeId);
							//save log
							a.getTower().getPlaneById(plane).addLog("Espera pista: "+Motor.getTimePassed(a.getTower().getPlaneById(plane).getLastStateDate()));
							Plane p = a.getTower().getPlaneById(this.plane);
							if(p.getWaitingTime()>=Retard.RETARD_TIME){
								long time = p.getWaitingTime();
								Measure.getRet().addLand(time);
							}
							a.getTower().getPlaneById(toLand).setLastStateDate();
							a.getTower().rmFirstFromLandList();
						}else if(toTakeOff != -1){
							//track is given to take off plane
							this.plane = toTakeOff;
							this.state = TrackState.TAKING_OFF;
							//save log
							a.getTower().getPlaneById(plane).addLog("Espera take off: "+Motor.getTimePassed(a.getTower().getPlaneById(toTakeOff).getLastStateDate()));
							Plane p = a.getTower().getPlaneById(this.plane);
							if(p.getWaitingTime()>=Retard.RETARD_TIME){
								long time = p.getWaitingTime();
								Measure.getRet().addTakeOff(time);
							}
							a.getTower().rmFirstFromTakeOffList();
							a.getTrack().setDelayToTakeOff();	
							a.getTower().getPlaneById(toTakeOff).setLastStateDate();
						}
					}
				}
			case TRACK_UNAVAILABLE:
				break;

			default:
				break;
			}
		}

		public void setPlane(int planeId) {
			this.plane = planeId;			
		}

		public void setDelayToTakeOff() {
			this.delay = 3;
		}

	}
	