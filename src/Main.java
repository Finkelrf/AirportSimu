import java.io.IOException;
import java.util.Random;


public class Main {
	
	
	public static boolean randomPlaneArriver(){
		if(Motor.getNowDate().getHours()<22 && Motor.getNowDate().getHours()>7){
			if(Motor.isWeekend()){
				//TODO random com media de 40 minutos
				if(new Random().nextInt(40) == 0){
					return true;
				}else{
					return false;
				}
			}else{
				if((Motor.getNowDate().getHours()>7 && Motor.getNowDate().getHours()>10)||(Motor.getNowDate().getHours()>17 && Motor.getNowDate().getHours()>19)){
					//TODO random com media de 10 minutos
					if(new Random().nextInt(10) == 0){
						return true;
					}else{
						return false;
					}
				}else{
					//TODO random com media de 20 minutos
					if(new Random().nextInt(20) == 0){
						return true;
					}else{
						return false;
					}
				}
			}
		}
		return false;
	}
	

	
	

	public static void main(String[] args) {
		Airport a = new Airport();
		Motor.setStartDate();
		try {
			CsvManager.csvManagerInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error to initiate Csv Manager");
		}
//		
//		for (int i = 0; i < 100; i++) {
//			int planeNumber = a.getTower().registerNewAproache(a);
//			a.getTower().addToLandList(planeNumber);
//			System.out.println("Plane P"+planeNumber+" is waiting to land");
//		}

		
		for (int i = 0; i < 3*30*24*60; i++) {
			//Step up the system
			Motor.stepUp();
			
			//add new plane
			if(randomPlaneArriver()){
				int planeNumber = a.getTower().registerNewAproache(a);
				a.getTower().addToLandList(planeNumber);
				System.out.println("Plane P"+planeNumber+" is waiting to land");
			}
						
			a.getTrack().handler(a);
			a.getTaxiways()[Taxiway.TAXIWAY_ARRIVE].handler(a);
			a.getTaxiways()[Taxiway.TAXIWAY_LEAVE].handler(a);
			for (int j = 0; j < a.getGates().length; j++) {
				a.getGates()[j].handler(a);
			}
			System.out.println(" ");
			
		}
		for (int i = 0; i < a.getTower().planeList.size(); i++) {
			a.getTower().getPlaneById(i).writeLog();
		}
		
		Measure.getRet().printAll();
		
	}
}
