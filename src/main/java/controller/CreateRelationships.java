package controller;


import java.io.IOException;
import java.util.Calendar;

public class CreateRelationships {
	
	public CreateRelationshipQuery Rela = new CreateRelationshipQuery();
	
	public CreateRelationships() {
		
	}
	
	public void createRelationship(int num) throws IOException {
		long begin = Calendar.getInstance().getTimeInMillis();

		Rela.queryString("Person","THAM_GIA","Event",Rela.Relationship(num, 1));
		Rela.queryString("Organization","TO_CHUC","Event",Rela.Relationship(num, 2));
		Rela.queryString("Person","DAI_DIEN","Event",Rela.Relationship(num, 3));
		Rela.queryString("Person","QUOC_TICH","Country",Rela.Relationship(num, 4));
		Rela.queryString("Person","DEN_THAM","Location",Rela.Relationship(num, 5));
		Rela.queryString("Location","DEN_THAM_VAO","Time",Rela.Relationship(num, 6));
		Rela.queryString("Event","DIEN_RA_TAI","Location",Rela.Relationship(num, 7));
		Rela.queryString("Event","DIEN_RA_VAO","Time",Rela.Relationship(num, 8));
		Rela.queryString("Organization","TRU_SO","Location",Rela.Relationship(num, 9));
		Rela.queryString("Location","THUOC","Country",Rela.Relationship(num, 10));
		
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("Thời gian tạo quan hệ: " + (end - begin)+" mili giây!");
	}
}
