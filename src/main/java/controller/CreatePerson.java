package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar; 
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import data.Data;
import model.Person;

public class CreatePerson implements ICreate{
	
	private String Tempid;
	Data data = new Data();
	
	@Override
	public String randomDinhDanh(int id) {
		return Tempid.replace(" ", "_")+id;
	}

	@Override
	public String randomMoTa() {
		String des = null;
		des = data.descriptionPerson[new
		                Random().nextInt(data.descriptionPerson.length)];
		return des;
	}
	
	@Override
	public String randomLink() {
		String link ;
		link = data.link[new Random().nextInt(data.link.length)];
		return link;
	}
	
	@Override
	public String randomThoiGian(int i) {
		Calendar calendar = Calendar.getInstance();
		i = i%6500; //Thời gian trích rút trong vòng 18 năm (6500 ngày) trở lại so với thời gian hiện tại 
		calendar.add(Calendar.DATE, -i);
		String a =  calendar.getTime().toString();
		return a.substring(0,11)+" "+a.substring(24,28);
	}
	
	@Override
	public String randomNhan() {
		String id;
		id = data.firstName[new Random().nextInt(data.firstName.length)]+" "+
			data.midName[new Random().nextInt(data.midName.length)]
			+" "+data.lastName[new Random().nextInt(data.lastName.length)];
		this.Tempid = id;
		return id;
	}
	
	public int randomAge() {
		
		return new Random().nextInt(90);
	}

	public String randomJob() {
		String job;
		job = data.job[new Random().nextInt(data.job.length)];
		return job;
	}

	public String randomQuocTich() {
		String qt;
		qt = data.quoctich[new Random().nextInt(data.quoctich.length)];
		return qt;
	}
	
	 public void saveCSV(int num) throws IOException {
	        String csvFile = "Person.csv";
	        Person Person = new Person();
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
	            CSVPrinter csvPrinter = new CSVPrinter(writer, 
	            CSVFormat.DEFAULT.withHeader("DinhDanh", "Nhan", "Mota", "LinkTrichRut", "ThoiGianTrichRut", "Age", "Job", "Quoctich"));) 
	        {
	        	System.out.println("Đang thêm node Person...");
				for(int i=0; i<num; i++) {	
					Person.setNhan(randomNhan());
					Person.setDinhdanh(randomDinhDanh(i));
					Person.setMota(randomMoTa());
					Person.setLink(randomLink());
					Person.setDate(randomThoiGian(i));
					Person.setAge(randomAge());
					Person.setJob(randomJob());
					Person.setQuoctich(randomQuocTich());
	            csvPrinter.printRecord(Person.getDinhdanh(), Person.getNhan(), Person.getMota(),
	            		Person.getLink(), Person.getDate(),Person.getAge(),Person.getJob(),Person.getQuoctich());
	            csvPrinter.flush();
	            
				}
	        }
	    }
	 
	@Override
	public void CreateNode(int num) {
		long begin = Calendar.getInstance().getTimeInMillis();
		Person Person = new Person();
		
		try {
			System.out.println("Đang thêm node Person...");
			for(int i=0; i<num; i++) {	
				Person.setNhan(randomNhan());
				Person.setDinhdanh(randomDinhDanh(i));
				Person.setMota(randomMoTa());
				Person.setLink(randomLink());
				Person.setDate(randomThoiGian(i));
				Person.setAge(randomAge());
				Person.setJob(randomJob());
				Person.setQuoctich(randomQuocTich());

		Connection.connection.execute("CREATE ("+Person.getDinhdanh()+":Person { "
					+ "DinhDanh: '"+Person.getDinhdanh()+"', "
					+ "Nhan: '"+Person.getNhan()+"', "
					+ "Mota: '"+Person.getMota()+"', "
					+ "LinkTrichRut: '"+Person.getLink()+"', "			
					+ "ThoiGianTrichRut: '"+Person.getDate()+"', "
					+ "Age: '"+Person.getAge()+"', "
					+ "Job: '"+Person.getJob()+"', "
					+ "Quoctich: '"+Person.getQuoctich()+"'})");
//		System.out.println("Đã thêm "+Person.getDinhdanh()+"!");
		}	
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("Thêm Person: " + (end - begin)+" mili giây!");
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}

}
