package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;  
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import data.Data;
import model.Location;

public class CreateLocation extends AbstractObject implements ILocation{

	private String Tempid;
	Data data = new Data();

	@Override
	public String randomNhan() {
		Tempid = data.location[new Random().nextInt(data.location.length)];
		return Tempid;
	}

	@Override
	public String randomMoTa() {
		return data.desquoctich[new Random().nextInt(data.desquoctich.length)];
	}
	
	@Override
	public String randomDinhDanh(int i) {
		String tem = (Tempid).replace(" ", "_");
		return tem+i;
	}
	
	@Override
	public String randomQuocGia() {
		return data.quoctich[new Random().nextInt(data.quoctich.length)];
	}
	
	@Override
	public void CreateNode(int num) {
		long begin = Calendar.getInstance().getTimeInMillis();
		Location Location = new Location();
		try {
			String csvFile = "Database/Location.csv";
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
		            CSVPrinter csvPrinter = new CSVPrinter(writer, 
		            CSVFormat.DEFAULT.withHeader("DinhDanh", "Nhan", "Mota", "LinkTrichRut", "ThoiGianTrichRut", "QuocGia"));) 
		        {
			System.out.println("Đang thêm node Location...");
			for(int i=0; i<num; i++) {	
				Location.setNhan(randomNhan());
				Location.setDinhdanh(randomDinhDanh(i));
				Location.setMota(randomMoTa());
				Location.setLink(randomLink());
				Location.setDate(randomThoiGian(i));
				Location.setQuocGia(randomQuocGia());
				
				csvPrinter.printRecord(Location.getDinhdanh(), Location.getNhan(), Location.getMota(),
	            		Location.getLink(), Location.getDate(),Location.getQuocGia());
	            csvPrinter.flush();
	            
				Connection.connection.execute("CREATE ("+Location.getDinhdanh()+":Location { "
					+ "DinhDanh: '"+Location.getDinhdanh()+"', "
					+ "Nhan: '"+Location.getNhan()+"', "
					+ "Mota: '"+Location.getMota()+"', "
					+ "LinkTrichRut: '"+Location.getLink()+"', "			
					+ "ThoiGianTrichRut:'"+Location.getDate()+"', "
					+ "QuocGia: '"+Location.getQuocGia()+"'})");
//				System.out.println("Da them "+Location.getDinhdanh()+"!");
		}	
				}
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("Thêm Location: " + (end - begin)+" mili giây!");
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}

}
