package controller;

import java.util.Calendar;

public class CreateRelationships {

	public CreateRelationships() {
	}
	
	public void CreateRelationship(int num) {
		long begin = Calendar.getInstance().getTimeInMillis();
		try {		
			Connection.connection.execute("MATCH (per:Person),(event:Event) WHERE per.Nhan <> event.DaiDien " + 
					"AND per.LinkTrichRut = event.LinkTrichRut "
					+ " WITH DISTINCT per, event LIMIT "+num+"  CREATE (per)-[:THAM_GIA]->(event)");
			
			Connection.connection.execute("MATCH (org:Organization),(event:Event) WHERE org.LinkTrichRut = event.LinkTrichRut"
					+ " AND org.TruSo = event.DiaDiem WITH DISTINCT org, event LIMIT "+num+" CREATE (org)-[:TO_CHUC]->(event)");
			
			Connection.connection.execute("MATCH (per:Person),(event:Event) WHERE per.Nhan = event.DaiDien " + 
					"AND per.LinkTrichRut = event.LinkTrichRut WITH DISTINCT per, event LIMIT "+num+" CREATE (per)-[:DAI_DIEN]->(event)");
			
			Connection.connection.execute("MATCH (per:Person),(country:Country) WHERE per.Quoctich = country.Nhan\r\n" + 
					"AND per.LinkTrichRut = country.LinkTrichRut WITH DISTINCT per, country LIMIT "+num+" CREATE (per)-[:QUOC_TICH]->(country)");
			
			Connection.connection.execute("MATCH (per:Person),(loca:Location),(time:Time) WHERE per.Quoctich <> loca.Nhan\r\n" + 
					"AND per.LinkTrichRut = loca.LinkTrichRut  "
					+ "AND time.LinkTrichRut = loca.LinkTrichRut "
					+ " AND per.LinkTrichRut = time.LinkTrichRut WITH DISTINCT per, loca LIMIT "+num*2
					+ " CREATE (per)-[:DEN_THAM]->(loca)");
			
			Connection.connection.execute("MATCH (per:Person)-[:DEN_THAM]->(loca:Location),(time:Time) WHERE per.Quoctich <> loca.Nhan\r\n" + 
					"AND per.LinkTrichRut = loca.LinkTrichRut "
					+ "AND time.LinkTrichRut = loca.LinkTrichRut "
					+ " AND per.LinkTrichRut = time.LinkTrichRut WITH DISTINCT loca,time LIMIT "+num*2
					+ " CREATE (loca)-[:DEN_THAM_VAO]->(time)");
		
			Connection.connection.execute("MATCH (event:Event),(loca:Location) WHERE event.DiaDiem = loca.Nhan\r\n" + 
					"AND event.LinkTrichRut = loca.LinkTrichRut "
					+ " WITH DISTINCT event, loca LIMIT "+num+" CREATE (event)-[:DIEN_RA_TAI]->(loca)");
			
			Connection.connection.execute("MATCH (event:Event),(time:Time) WHERE " + 
					"  event.ThoiGianToChuc = time.Nhan"
					+ " WITH DISTINCT event, time LIMIT "+num+" CREATE (event)-[:DIEN_RA_VAO]->(time)");
					
			Connection.connection.execute("MATCH (org:Organization),(loca:Location) WHERE org.TruSo = loca.Nhan\r\n" + 
					"AND org.LinkTrichRut = loca.LinkTrichRut WITH DISTINCT org, loca LIMIT "+(num)+" CREATE UNIQUE (org)-[:TRU_SO]->(loca)");
			
			Connection.connection.execute("MATCH (loca:Location),(country:Country) WHERE loca.QuocGia = country.Nhan\r\n" + 
					"AND loca.LinkTrichRut = country.LinkTrichRut WITH DISTINCT loca, country LIMIT "+num+" CREATE (loca)-[:THUOC]->(country)");
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
		}
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("Thời gian tạo quan hệ: " + (end - begin)+" mili giây!");
	}
}
