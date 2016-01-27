import java.util.Random;



public class Main {
	
	
	public static boolean randomPlaneArriver(){
		if(Motor.getNowDate().getHours()<22 && Motor.getNowDate().getHours()>7){
			if(Motor.isWeekend()){
				//TODO random com media de 40 minutos
				return new Random().nextBoolean();
			}else{
				if((Motor.getNowDate().getHours()>7 && Motor.getNowDate().getHours()>10)||(Motor.getNowDate().getHours()>17 && Motor.getNowDate().getHours()>19)){
					//TODO random com media de 10 minutos
					return new Random().nextBoolean();
				}else{
					//TODO random com media de 20 minutos
					return new Random().nextBoolean();	
				}
			}
		}
		return false;
	}
	

	
	

	public static void main(String[] args) {
		Airport a = new Airport();
		Motor.setStartDate();
		
		for (int i = 0; i < 50; i++) {
			int planeNumber = a.getTour().registerNewAproache();
			System.out.println("Plane P"+planeNumber+" is waiting to land");
		}

		
		for (int i = 0; i < 500; i++) {
			//Step up the system
			Motor.stepUp();
			
			//add new plane
			if(randomPlaneArriver()){
				int planeNumber = a.getTour().registerNewAproache();
				System.out.println("Plane P"+planeNumber+" is waiting to land");
			}
			
			
			//check if is possible to land
			if(a.getTrack().isAvailable() && a.getTaxiways()[1].isAvailable() && a.getTour().isPlaneWaitingToLand()){
				System.out.println("Land permitted");
				//set track as unavailable
				a.getTrack().occupy();
				a.getTrack().setDelayToLand();
				int planeId = a.getTour().sendPermissionToLand();
				a.getTrack().setPlane(planeId);
			}
			
			a.getTrack().handler(a);
			a.getTaxiways()[Airport.TAXIWAY_ARRIVE].handler(a);
			//a.getTaxiways()[Airport.TAXIWAY_LEAVE].handler(a);
			
			
			
			
		}
		
	}
}
