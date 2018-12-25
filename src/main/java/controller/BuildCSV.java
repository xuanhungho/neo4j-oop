package controller;

import java.io.IOException;
import java.util.Calendar;

import model.Country;
import model.Event;
import model.Location;
import model.Organization;
import model.Person;
import model.Time;

public class BuildCSV {

	public BuildCSV() {
		// TODO Auto-generated constructor stub
	}

	public void createCSV(int numEntity, int numRela ) throws IOException {
		CreateRelationshipQuery.CSV = true;
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
		
		CreateNodePerson createPer = new CreateNodePerson();
		Person per = new Person();
		
		createPer.createCSV(per, numEntity);
		
		CreateNodeOrganization createOrg = new CreateNodeOrganization();
		Organization Org = new Organization();
		createOrg.createCSV(Org, numEntity);
		
		CreateNodeLocation createLocation = new CreateNodeLocation();
		Location loca = new Location();
		createLocation.createCSV(loca, numEntity);
		
		CreateNodeEvent createEvent = new CreateNodeEvent();
		Event event = new Event();
		createEvent.createCSV(event, numEntity);
		
		CreateNodeCountry createCountry = new CreateNodeCountry();
		Country country = new Country();
		createCountry.createCSV(country, numEntity);
		
		CreateNodeTime createTime = new CreateNodeTime();
		Time time = new Time();
		createTime.createCSV(time, numEntity);
		
		System.out.println("Đang ghi CSV quan hệ...");
		CreateRelationships Rela = new CreateRelationships();
		Rela.createRelationship(numRela);
		
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("Done: " + (end - begin)+" mili giây!");

	}
}
