import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import au.com.bytecode.opencsv.*;

public class CsvManager {
	DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmss"); 
	Date date = new Date();
	private String outputFile = dateFormat.format(date)+"-log.csv";
	
	private CSVWriter csvOutput;
	
	private ArrayList<String[]> dataList;

	public CsvManager() throws IOException{
		
		dataList = new ArrayList<String[]>();
		
		csvOutput = new CSVWriter(new FileWriter(outputFile),'\t');
		boolean alreadyExists = new File(outputFile).exists();
		System.out.println(alreadyExists);
				
		if (!alreadyExists)
		{
			csvOutput.writeNext("id");
			csvOutput.writeNext("name");
			System.out.println("aqui");
		}
	}
	
	public void registerData(String[] data) throws IOException{
		csvOutput = new CSVWriter(new FileWriter(outputFile),'\t');
		dataList.add(data);
	    csvOutput.writeAll(dataList);      
	    csvOutput.close();	    
	}
	
	public void registerData(String data, int row, int column) throws IOException{
		csvOutput = new CSVWriter(new FileWriter(outputFile),';');
		dataList.get(row)[column] = data;
	    csvOutput.writeAll(dataList);      
	    csvOutput.close();	    
	}
	
	public void readData(){
		
	}
	
	private void addData(String[] data){
		this.dataList.add(data);
	}
	
	public void setData(String data, int row, int column){
		this.dataList.get(row)[column] = data;
	}
	
	public String[] getData(int row){
		return this.dataList.get(row);
	}
	
	public String getData(int row, int column){
		return this.dataList.get(row)[column];
	}
	
	public ArrayList<String[]> getDataList(){
		return dataList;
	}
	
	

}
