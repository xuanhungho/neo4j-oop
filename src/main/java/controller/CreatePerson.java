package controller;

import java.io.BufferedWriter; 
import java.io.FileWriter;
import java.util.Calendar; 
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import data.Data;
import model.Person;

public class CreatePerson extends AbstractObject implements IPerson{
	
	private String Tempid;
	Data data = new Data();
	
	@Override
	public String randomDinhDanh(int id) {
		return Tempid.replace(" ", "_")+id;
	}

	@Override
	public String randomMoTa() {
		return data.descriptionPerson[new Random().nextInt(data.descriptionPerson.length)];
	}
	
	@Override
	public String randomNhan() {
		String id;
		id = data.firstName[new Random().nextInt(data.firstName.length)]+" "+
			data.midName[new Random().nextInt(data.midName.length)]
			+" "+data.lastName[new Random().nextInt(data.lastName.length)];
		Tempid = id;
		return id;
	}
	
	@Override
	public int randomAge() {
		return new Random().nextInt(90);
	}

	@Override
	public String randomJob() {
		return data.job[new Random().nextInt(data.job.length)];
	}

	@Override
	public String randomQuocTich() {
		return data.quoctich[new Random().nextInt(data.quoctich.length)];
	}
	 
	@Override
	public void CreateNode(int num) {
		long begin = Calendar.getInstance().getTimeInMillis();
		Person Person = new Person();
		try {
			
			String csvFile = "Database/Person.csv";
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
		            CSVPrinter csvPrinter = new CSVPrinter(writer, 
		            CSVFormat.DEFAULT.withHeader("DinhDanh:ID(Person)", "Nhan", "Mota", "LinkTrichRut", "ThoiGianTrichRut", "Age", "Job", "Quoctich"));) 
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
		}
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("Thêm Person: " + (end - begin)+" mili giây!");
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}

}
