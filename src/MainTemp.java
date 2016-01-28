import java.io.IOException;
import java.util.Random;


public class MainTemp {

	public static void main(String[] args) {
		
//		try {
//			CsvManager cm = new CsvManager();
//			
//			cm.registerData(new String []{"10","20","30","40","50"});
//			cm.registerData(new String []{"10","20","30","30","40","50"});
//			cm.registerData(new String []{"10","20","30","30","40","50"});
//			cm.registerData(new String []{"10","20","30","30","40","60"});
//				
//			cm.registerData("teste", 2, 2);
//			
//			System.out.println(cm.getData(2));
//			System.out.println(cm.getData(2, 2));
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Fecha o arquivo fdp..");
//		}
		
		
		try {
			CsvManager.csvManagerInit();
			
			CsvManager.registerData(new String []{"10","20","30","40","50"});
			CsvManager.registerData(new String []{"10","20","30","30","40","50"});
			CsvManager.registerData(new String []{"10","20","30","30","40","50"});
			CsvManager.registerData(new String []{"10","20","30","30","40","60"});
				
			CsvManager.registerData("lelelel", 2, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
