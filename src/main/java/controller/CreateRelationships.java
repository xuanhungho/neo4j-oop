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
	
	public boolean graph = true;
	
	public CreateRelationships() {
	}
	
	public void queryString(String Start,String Rela, String End, String Query) throws IOException {
    	System.out.println("Đang tạo quan hệ "+Rela+"...");
    	 try (Session session = Connection.connection.driver.session()){
    		 
    		File fileDir = new File("Database/"+Rela+".csv");
    		Writer out;
    		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF-8"));
 			out.write("");
 			out.append(":START_ID("+Start+"),:END_ID("+End+")");
 			out.append("\r\n");
 			out.flush();
            StatementResult result = session.run(Query);
            while (result.hasNext())
             {
                 Record record = result.next();
                 out.append(record.get("start").asString()+","+record.get("end").asString());
                 out.append("\r\n");
                 out.flush();
             }
             out.close();
         }
    }

	public void CreateRelationship(int num) throws IOException {
		long begin = Calendar.getInstance().getTimeInMillis();
		String q1 = "MATCH (per:Person),(event:Event) WHERE per.Nhan <> event.DaiDien "
    			+ " AND per.LinkTrichRut = event.LinkTrichRut WITH DISTINCT per, event LIMIT "+num;
		if(graph) {
			q1+=" CREATE (per)-[:THAM_GIA]->(event) ";
		}
		q1+=" return per.DinhDanh AS start, event.DinhDanh AS end";
		
		String q2 ="MATCH (org:Organization),(event:Event) WHERE org.LinkTrichRut = event.LinkTrichRut"
				+ " AND org.TruSo = event.DiaDiem WITH DISTINCT org, event LIMIT "+num;
		if(graph) { 
			q2+=" CREATE (org)-[:TO_CHUC]->(event)";
		}
		q2+=" return org.DinhDanh AS start, event.DinhDanh AS end";
		
		String q3="MATCH (per:Person),(event:Event) WHERE per.Nhan = event.DaiDien " + 
				"AND per.LinkTrichRut = event.LinkTrichRut WITH DISTINCT per, event LIMIT "+num;
		if(graph) {
			q3+=" CREATE (per)-[:DAI_DIEN]->(event)";
		}
		q3+= " return per.DinhDanh AS start, event.DinhDanh AS end";
		
		String q4 ="MATCH (per:Person),(country:Country) WHERE per.Quoctich = country.Nhan\r\n" + 
					"AND per.LinkTrichRut = country.LinkTrichRut WITH DISTINCT per, country LIMIT "+num;
		if(graph) { 
		q4+=" CREATE (per)-[:QUOC_TICH]->(country)";
		}
		q4+=" return per.DinhDanh AS start, country.DinhDanh AS end";
			
		String q5 ="MATCH (per:Person),(loca:Location),(time:Time) WHERE per.Quoctich <> loca.Nhan\r\n" + 
					"AND per.LinkTrichRut = loca.LinkTrichRut  "
					+ "AND time.LinkTrichRut = loca.LinkTrichRut "
					+ " AND per.LinkTrichRut = time.LinkTrichRut WITH DISTINCT per, loca LIMIT "+num;
		if(graph) {
			q5+=" CREATE (per)-[:DEN_THAM]->(loca)";
		}
		q5+=" return per.DinhDanh AS start, loca.DinhDanh AS end";
			
		String q6 ="MATCH (per:Person)-[:DEN_THAM]->(loca:Location),(time:Time) WHERE per.Quoctich <> loca.Nhan\r\n" + 
					"AND per.LinkTrichRut = loca.LinkTrichRut "
					+ "AND time.LinkTrichRut = loca.LinkTrichRut "
					+ " AND per.LinkTrichRut = time.LinkTrichRut WITH DISTINCT loca,time LIMIT "+num;
		if(graph) {
			q6+=" CREATE (loca)-[:DEN_THAM_VAO]->(time)";
		}
		q6+=" return loca.DinhDanh AS start, time.DinhDanh AS end";
		
		String q7 ="MATCH (event:Event),(loca:Location) WHERE event.DiaDiem = loca.Nhan " + 
					" AND event.LinkTrichRut = loca.LinkTrichRut "
					+ " WITH DISTINCT event, loca LIMIT "+num+" ";
		if(graph) {
			q7+=" CREATE (event)-[:DIEN_RA_TAI]->(loca)";
		}
		q7+=" return loca.DinhDanh AS start, event.DinhDanh AS end";
			
		String q8 ="MATCH (event:Event),(time:Time) WHERE " + 
					"  event.ThoiGianToChuc = time.Nhan"
					+ " WITH DISTINCT event, time LIMIT "+num;
		if(graph) {
			q8+=" CREATE (event)-[:DIEN_RA_VAO]->(time)";
		}
		q8+=" return event.DinhDanh AS start, time.DinhDanh AS end";
					
		String q9 ="MATCH (org:Organization),(loca:Location) WHERE org.TruSo = loca.Nhan\r\n" + 
					"AND org.LinkTrichRut = loca.LinkTrichRut WITH DISTINCT org, loca LIMIT "+(num)+" ";
		if(graph) {
			q9+=" CREATE UNIQUE (org)-[:TRU_SO]->(loca)";
		}
		q9+=" return loca.DinhDanh AS start, org.DinhDanh AS end";
			
		String q10 ="MATCH (loca:Location),(country:Country) WHERE loca.QuocGia = country.Nhan\r\n" + 
					"AND loca.LinkTrichRut = country.LinkTrichRut WITH DISTINCT loca, country LIMIT "+num;
		if(graph) {
			q10+=" CREATE (loca)-[:THUOC]->(country)";
		}
		q10+=" return loca.DinhDanh AS start, country.DinhDanh AS end";
		
		queryString("Person","THAM_GIA","Event",q1);
		queryString("Organization","TO_CHUC","Event",q2);
		queryString("Organization","DAI_DIEN","Event",q3);
		queryString("Person","QUOC_TICH","Country",q4);
		queryString("Person","DEN_THAM","Location",q5);
		queryString("Location","DEN_THAM_VAO","Time",q6);
		queryString("Event","DIEN_RA_TAI","Location",q7);
		queryString("Event","DIEN_RA_VAO","Time",q8);
		queryString("Organization","TRU_SO","Location",q9);
		queryString("Location","THUOC","Country",q10);

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("Thời gian tạo quan hệ: " + (end - begin)+" mili giây!");
		}
		
}
