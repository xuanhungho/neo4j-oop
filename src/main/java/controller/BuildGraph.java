package controller;

import java.io.IOException;
import java.util.Calendar;

public class BuildGraph {

	public BuildGraph() {
		// TODO Auto-generated constructor stub
	}
	
	public void createGraph(int numEntity, int numRela ) throws IOException {
		numEntity = numEntity/6;
		numRela = numRela/10;
		
		long begin = Calendar.getInstance().getTimeInMillis();
		
		System.out.println("Đang xoá các node cũ...");
		try {
			Connection.connection.removeData();
			} catch (Exception e) {
				System.out.println(e.getMessage());
		}
		
		System.out.println("Đang tạo node...");
		CreatePerson per = new CreatePerson();
		per.CreateNode(numEntity);
		
		CreateOrganization Org = new CreateOrganization();
		Org.CreateNode(numEntity);
		
		CreateLocation location = new CreateLocation();
		location.CreateNode(numEntity);
		
		CreateEvent event = new CreateEvent();
		event.CreateNode(numEntity);
		
		CreateCountry country = new CreateCountry();
		country.CreateNode(numEntity);
		
		CreateTime time = new CreateTime();
		time.CreateNode(numEntity);
		
		System.out.println("Đang tạo quan hệ...");
		CreateRelationships Rela = new CreateRelationships();
		Rela.CreateRelationship(numRela);
		
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("Done: " + (end - begin)+" mili giây!");

	}
}
