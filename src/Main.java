

public class Main {
	
	
	public static boolean requestToLand(int i){
		if (i==3)
			return true;
		else
			return false;
	}

	public static void main(String[] args) {
		Plane p = null;
		Airport a = new Airport();
		Motor.setStartDate();
		
		for (int i = 0; i < 100; i++) {
			//Step up the system
			Motor.stepUp();

			if(i==2){
				//plane arrive 
				p =  new Plane(Motor.getNowDate());
				p.setId(2);
			}
			if(Main.requestToLand(i)){
				p.permissionToLand();	
				System.out.println(Motor.getTimePassed(p.getLastStateDate()));
			}
		}
		
	}
}
