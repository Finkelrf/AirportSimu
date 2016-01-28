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
		
		for (int i = 0; i < 100; i++) {
			int planeNumber = a.getTower().registerNewAproache(a);
			a.getTower().addToLandList(planeNumber);
			System.out.println("Plane P"+planeNumber+" is waiting to land");
		}

		
		for (int i = 0; i < 1000; i++) {
			//Step up the system
			Motor.stepUp();
			
			//add new plane
//			if(randomPlaneArriver()){
//				int planeNumber = a.getTower().registerNewAproache();
//				a.getTower().addToLandList(planeNumber);
//				System.out.println("Plane P"+planeNumber+" is waiting to land");
//			}
			
			
	
			
			a.getTrack().handler(a);
			a.getTaxiways()[Taxiway.TAXIWAY_ARRIVE].handler(a);
			a.getTaxiways()[Taxiway.TAXIWAY_LEAVE].handler(a);
			for (int j = 0; j < a.getGates().length; j++) {
				a.getGates()[j].handler(a);
			}
			
			
			
			
		}
		
	}
}
