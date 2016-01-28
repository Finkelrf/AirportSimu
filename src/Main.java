import java.io.IOException;
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
		
		try {
			CsvManager cm = new CsvManager();
			
			cm.registerData(new String []{"10","20","30","40","50"});
			cm.registerData(new String []{"10","20","30","30","40","50"});
			cm.registerData(new String []{"10","20","30","30","40","50"});
			cm.registerData(new String []{"10","20","30","30","40","60"});
			
			cm.registerData("teste", 2, 2);
			
			System.out.println(cm.getData(2));
			System.out.println(cm.getData(2, 2));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Fecha o arquivo fdp..");
		}
		
		
		for (int i = 0; i < 50; i++) {
			int planeNumber = a.getTower().registerNewAproache();
			System.out.println("Plane P"+planeNumber+" is waiting to land");
		}

		
		for (int i = 0; i < 500; i++) {
			//Step up the system
			Motor.stepUp();
			
			//add new plane
			if(randomPlaneArriver()){
				int planeNumber = a.getTower().registerNewAproache();
				System.out.println("Plane P"+planeNumber+" is waiting to land");
			}
			
			
			//check if is possible to land
			if(a.getTrack().isAvailable() && a.getTaxiways()[1].isAvailable() && a.getTower().isPlaneWaitingToLand()){
				System.out.println("Land permitted");
				//set track as unavailable
				a.getTrack().occupy();
				a.getTrack().setDelayToLand();
				int planeId = a.getTower().sendPermissionToLand();
				a.getTrack().setPlane(planeId);
			}
			
			a.getTrack().handler(a);
			a.getTaxiways()[Airport.TAXIWAY_ARRIVE].handler(a);
			//a.getTaxiways()[Airport.TAXIWAY_LEAVE].handler(a);
			for (int j = 0; j < a.getGates().length; j++) {
				a.getGates()[j].handler(a);
			}
			
			
			
			
		}
		
	}
}
