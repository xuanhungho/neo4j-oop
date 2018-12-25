package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import data.Data;
import model.Time;

public class CreateTime extends AbstractObject{
	private String Tempid;
	
	Data data = new Data();

	@Override
	public String randomNhan() {
		return Tempid;
	}

	@Override
	public String randomMoTa() {
		return Tempid;
	}
	
	@Override
	public String randomDinhDanh(int i) {
		Calendar calendar = Calendar.getInstance();
		i = i%6500;
		calendar.add(Calendar.DATE, -i);
		String a =  calendar.getTime().toString();
		Tempid = a.substring(0,11)+" "+a.substring(24,28);
		return Tempid.replace(" ","_")+i;
	}
	
	@Override
	public void CreateNode(int num) {
		long begin = Calendar.getInstance().getTimeInMillis();
		Time Time = new Time();

		try {
			String csvFile = "Database/Time.csv";
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
		            CSVPrinter csvPrinter = new CSVPrinter(writer, 
		            CSVFormat.DEFAULT.withHeader("DinhDanh:ID(Time)", "Nhan", "Mota", "LinkTrichRut", "ThoiGianTrichRut"));) 
		        {
			System.out.println("Đang thêm node Time...");
				for(int i=0; i<num; i++) {	
				Time.setNhan(randomNhan());
				Time.setDinhdanh(randomDinhDanh(i));
				Time.setMota(randomMoTa());
				Time.setLink(randomLink());
				Time.setDate(randomThoiGian(i));

				csvPrinter.printRecord(Time.getDinhdanh(), Time.getNhan(), Time.getMota(),
	            		Time.getLink(), Time.getDate());
	            csvPrinter.flush();
	            
				Connection.connection.execute("CREATE ("+Time.getDinhdanh()+":Time { "
					+ "DinhDanh: '"+Time.getDinhdanh()+"', "
					+ "Nhan: '"+Time.getNhan()+"', "
					+ "Mota: '"+Time.getMota()+"', "
					+ "LinkTrichRut: '"+Time.getLink()+"', "			
					+ "ThoiGianTrichRut: '"+Time.getDate()+"'})");
//				System.out.println("Da them "+Time.getDinhdanh()+"!");
		}	
		        }
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("Thời gian thực hiện: " + (end - begin)+" mili giây!");
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}
}
