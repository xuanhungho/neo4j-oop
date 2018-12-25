package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;  
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import data.Data;
import model.Event;

public class CreateEvent extends AbstractObject implements IEvent{
	
	private String Tempid;

	Data data = new Data();

	@Override
	public String randomNhan() {
		Tempid = data.event[new Random().nextInt(data.event.length)];
		return Tempid;
	}

	@Override
	public String randomMoTa() {
		return data.event[new Random().nextInt(data.event.length)];
	}
	
	@Override
	public String randomDinhDanh(int i) { 
		return (Tempid).replace(" ", "_")+i;
	}
	
	@Override
	public String randomDiaDiem() {
		return data.location[new Random().nextInt(data.location.length)];
	}
	
	@Override
	public String randomDaiDien() {
		String nhan = null ;
		nhan = data.firstName[new Random().nextInt(data.firstName.length)]+" "+
				data.midName[new Random().nextInt(data.midName.length)]+" "+
				data.lastName[new Random().nextInt(data.lastName.length)];
		return nhan;
	}

	@Override
	public String randomTime(int i) {
		Calendar calendar = Calendar.getInstance();
		i = i%6500+new Random().nextInt(15);
		calendar.add(Calendar.DATE, -i);
		String a =  calendar.getTime().toString();
		return a.substring(0,11)+" "+a.substring(24,28);
	}
	
	@Override
	public void CreateNode(int num) {
		long begin = Calendar.getInstance().getTimeInMillis();
		Event Event = new Event();
		try {	
			String csvFile = "Database/Event.csv";
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
		            CSVPrinter csvPrinter = new CSVPrinter(writer, 
		            CSVFormat.DEFAULT.withHeader("DinhDanh:ID(Event)", "Nhan", "Mota", "LinkTrichRut", "ThoiGianTrichRut", "DiaDiem", "ThoiGianToChuc","DaiDien"));) 
		        {
			System.out.println("Đang thêm node Event...");
			for(int i=0; i<num; i++) {	
				Event.setNhan(randomNhan());
				Event.setDinhdanh(randomDinhDanh(i));
				Event.setMota(randomMoTa());
				Event.setLink(randomLink());
				Event.setDate(randomThoiGian(i));
				Event.setDaidientochuc(randomDaiDien());
				Event.setDiadiem(randomDiaDiem());
				Event.setTime(randomTime(i));
				
				csvPrinter.printRecord(Event.getDinhdanh(), Event.getNhan(), Event.getMota(),
	            		Event.getLink(), Event.getDate(),Event.getDiadiem(),Event.getTime(),Event.getDaidientochuc());
	            csvPrinter.flush();
	            
				Connection.connection.execute("CREATE ("+Event.getDinhdanh()+":Event { "
					+ "Nhan: '"+Event.getNhan()+"', "
					+ "DinhDanh: '"+Event.getDinhdanh()+"', "
					+ "Mota: '"+Event.getMota()+"', "
					+ "LinkTrichRut: '"+Event.getLink()+"', "			
					+ "ThoiGianTrichRut: '"+Event.getDate()+"', "
					+ "DiaDiem: '"+Event.getDiadiem()+"', "
					+ "ThoiGianToChuc: '"+Event.getTime()+"', "
					+ "DaiDien: '"+Event.getDaidientochuc()+"'})");
		
//				System.out.println("Da them "+Event.getDinhdanh()+"!");
		}	
		        }
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("Thêm Event: " + (end - begin)+" mili giây!");
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}

}
