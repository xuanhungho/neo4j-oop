package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;  
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import data.Data;
import model.Country;

public class CreateCountry extends AbstractObject{
	
	private String Tempid;
	
	Data data = new Data();

	@Override
	public String randomNhan() {
		String nhan = null ;
		nhan = data.quoctich[new Random().nextInt(data.quoctich.length)];
		Tempid = nhan;
		return nhan;
	}

	@Override
	public String randomMoTa() {
		String des = null;
		des = data.desquoctich[new Random().nextInt(data.desquoctich.length)];
		return des;
	}
	
	@Override
	public String randomDinhDanh(int i) {
		String tem = (Tempid).replace(" ", "_");
		return tem+i;
	}
	
	@Override
	public void CreateNode(int num) {
		long begin = Calendar.getInstance().getTimeInMillis();
		Country Country = new Country();
		try {	
			String csvFile = "Database/Country.csv";
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
		            CSVPrinter csvPrinter = new CSVPrinter(writer, 
		            CSVFormat.DEFAULT.withHeader("DinhDanh", "Nhan", "Mota", "LinkTrichRut", "ThoiGianTrichRut"));) 
		        {
			System.out.println("Đang thêm node Country...");
			for(int i=0; i<num; i++) {	
				Country.setNhan(randomNhan());
				Country.setDinhdanh(randomDinhDanh(i));
				Country.setMota(randomMoTa());
				Country.setLink(randomLink());
				Country.setDate(randomThoiGian(i));
				
				csvPrinter.printRecord(Country.getDinhdanh(), Country.getNhan(), Country.getMota(),
	            		Country.getLink(), Country.getDate());
	            csvPrinter.flush();
	            
				Connection.connection.execute("CREATE ("+Country.getDinhdanh()+":Country { "
					+ "DinhDanh: '"+Country.getDinhdanh()+"', "
					+ "Nhan: '"+Country.getNhan()+"', "
					+ "Mota: '"+Country.getMota()+"', "
					+ "LinkTrichRut: '"+Country.getLink()+"', "			
					+ "ThoiGianTrichRut: '"+Country.getDate()+"'})");
//				System.out.println("Đã thêm "+Country.getDinhdanh()+"!");
		}	
		        }
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("Thêm Country: " + (end - begin)+" mili giây!");
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}
}
