package controller;


import java.io.BufferedWriter;  
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class CreateRelationships {
	
	public CreateRelationshipQuery Rela = new CreateRelationshipQuery();
	
	public CreateRelationships() {
		
	}
	
	public void queryString(String Start,String Relationships, String End, String Query) throws IOException {
    	System.out.println("Đang tạo quan hệ "+Relationships+"...");
    	 try (Session session = Connection.connection.driver.session()){
    		StatementResult result = session.run(Query);
    		if(CreateRelationshipQuery.CSV) {
    		File fileDir = new File("Database/"+Relationships+".csv");
    		Writer out;
    		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF-8"));
 			out.write("");
 			out.append(":START_ID("+Start+"),:END_ID("+End+")");
 			out.append("\r\n");
 			out.flush();
            	while (result.hasNext()){
                 Record record = result.next();
                 out.append(record.get("start").asString()+","+record.get("end").asString());
                 out.append("\r\n");
                 out.flush();
             }
            out.close();
    		}
         }
    }
	
	public void createRelationship(int num) throws IOException {
		long begin = Calendar.getInstance().getTimeInMillis();

		queryString("Person","THAM_GIA","Event",Rela.Relationship(num, 1));
		queryString("Organization","TO_CHUC","Event",Rela.Relationship(num, 2));
		queryString("Organization","DAI_DIEN","Event",Rela.Relationship(num, 3));
		queryString("Person","QUOC_TICH","Country",Rela.Relationship(num, 4));
		queryString("Person","DEN_THAM","Location",Rela.Relationship(num, 5));
		queryString("Location","DEN_THAM_VAO","Time",Rela.Relationship(num, 6));
		queryString("Event","DIEN_RA_TAI","Location",Rela.Relationship(num, 7));
		queryString("Event","DIEN_RA_VAO","Time",Rela.Relationship(num, 8));
		queryString("Organization","TRU_SO","Location",Rela.Relationship(num, 9));
		queryString("Location","THUOC","Country",Rela.Relationship(num, 10));
		
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("Thời gian tạo quan hệ: " + (end - begin)+" mili giây!");
	}
}
