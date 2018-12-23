package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;  
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import data.Data;
import model.Organization;

public class CreateOrganization extends AbstractObject implements IOrganization{

	private String Tempid;
	Data data = new Data();

	@Override
	public String randomNhan() {
		Tempid = data.nameOrganization[new Random().nextInt(data.nameOrganization.length)];
		return Tempid;
	}

	@Override
	public String randomMoTa() {
		return data.descriptionOrganization[new Random().nextInt(data.descriptionOrganization.length)];
	}

	@Override
	public String randomTruSo() {
		return data.location[new Random().nextInt(data.location.length)];
	}
	
	@Override
	public String randomDinhDanh(int i) {
		String tem = Tempid.replace(" ", "_");
		return tem+i;
	}
	
	@Override
	public void CreateNode(int num) {
		long begin = Calendar.getInstance().getTimeInMillis();
		Organization Org = new Organization();
		try {
			String csvFile = "Database/Organization.csv";
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
		            CSVPrinter csvPrinter = new CSVPrinter(writer, 
		            CSVFormat.DEFAULT.withHeader("DinhDanh", "Nhan", "Mota", "LinkTrichRut", "ThoiGianTrichRut", "TruSo"));) 
		        {
			System.out.println("Đang thêm node Organization...");
			for(int i=0; i<num; i++) {	
				Org.setNhan(randomNhan());
				Org.setDinhdanh(randomDinhDanh(i));
				Org.setMota(randomMoTa());
				Org.setLink(randomLink());
				Org.setDate(randomThoiGian(i));
				Org.setTruSo(randomTruSo());
				
				csvPrinter.printRecord(Org.getDinhdanh(), Org.getNhan(), Org.getMota(),
	            		Org.getLink(), Org.getDate(),Org.getTruSo());
	            csvPrinter.flush();
	            
				Connection.connection.execute("CREATE ("+Org.getDinhdanh()+":Organization { "
					+ "DinhDanh: '"+Org.getDinhdanh()+"', "
					+ "Nhan: '"+Org.getNhan()+"', "
					+ "Mota: '"+Org.getMota()+"', "
					+ "LinkTrichRut: '"+Org.getLink()+"', "			
					+ "ThoiGianTrichRut: '"+Org.getDate()+"', "
					+ "TruSo: '"+Org.getTruSo()+"'})");
//			System.out.println("Da them Org "+Org.getDinhdanh()+"!");
		}	
		        }
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("Thêm Organization: " + (end - begin)+" mili giây!");
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}
}
