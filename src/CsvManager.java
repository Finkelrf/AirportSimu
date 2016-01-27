import com.csvreader.*;
import java.io.File;
import java.io.IOException;

public class CsvManager {
	
	private String outputFile = "data.csv";
	
	private CsvWriter csvOutput;
	
	public CsvManager() throws IOException{
		
		csvOutput = new CsvWriter(outputFile);
		boolean alreadyExists = new File(outputFile).exists();
		
		if (!alreadyExists)
		{
			csvOutput.write("id");
			csvOutput.write("name");
			csvOutput.endRecord();
		}
	}
	
	public void registerData(int id, String name){
		try {
			csvOutput.write(""+id);
			csvOutput.write(name);
			csvOutput.endRecord();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readData(){
		
	}
}
