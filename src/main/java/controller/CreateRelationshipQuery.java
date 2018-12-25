package controller;

import java.util.ArrayList;

public class CreateRelationshipQuery {

	public static boolean GRAPH = false;
	public static boolean CSV = false;
	
	public CreateRelationshipQuery() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("null")
	public String Relationship(int num, int query) {
		ArrayList<String> q = new ArrayList<String>();
		
		String q1 ="MATCH (per:Person),(event:Event) WHERE per.Nhan <> event.DaiDien "
    			+ " AND per.LinkTrichRut = event.LinkTrichRut WITH DISTINCT per, event LIMIT "+num;
		if(GRAPH) q1+=" CREATE (per)-[:THAM_GIA]->(event) ";
		if(CSV) q1+=" return per.DinhDanh AS start, event.DinhDanh AS end";
		
		String q2 ="MATCH (org:Organization),(event:Event) WHERE org.LinkTrichRut = event.LinkTrichRut"
				+ " AND org.TruSo = event.DiaDiem WITH DISTINCT org, event LIMIT "+num;
		if(GRAPH) q2+=" CREATE (org)-[:TO_CHUC]->(event)";
		if(CSV) q2+=" return org.DinhDanh AS start, event.DinhDanh AS end";
		
		String q3="MATCH (per:Person),(event:Event) WHERE per.Nhan = event.DaiDien " + 
				"AND per.LinkTrichRut = event.LinkTrichRut WITH DISTINCT per, event LIMIT "+num;
		if(GRAPH) q3+=" CREATE (per)-[:DAI_DIEN]->(event)";
		if(CSV) q3+= " return per.DinhDanh AS start, event.DinhDanh AS end";
		
		String q4="MATCH (per:Person),(country:Country) WHERE per.Quoctich = country.Nhan\r\n" + 
					"AND per.LinkTrichRut = country.LinkTrichRut WITH DISTINCT per, country LIMIT "+num;
		if(GRAPH) q4+=" CREATE (per)-[:QUOC_TICH]->(country)";
		if(CSV) q4+=" return per.DinhDanh AS start, country.DinhDanh AS end";
			
		String q5 ="MATCH (per:Person),(loca:Location),(time:Time) WHERE per.Quoctich <> loca.Nhan\r\n" + 
					"AND per.LinkTrichRut = loca.LinkTrichRut  "
					+ "AND time.LinkTrichRut = loca.LinkTrichRut "
					+ " AND per.LinkTrichRut = time.LinkTrichRut WITH DISTINCT per, loca LIMIT "+num;
		if(GRAPH) q5+=" CREATE (per)-[:DEN_THAM]->(loca)";
		if(CSV) q5+=" return per.DinhDanh AS start, loca.DinhDanh AS end";
			
		String q6 ="MATCH (per:Person)-[:DEN_THAM]->(loca:Location),(time:Time) WHERE per.Quoctich <> loca.Nhan\r\n" + 
					"AND per.LinkTrichRut = loca.LinkTrichRut "
					+ "AND time.LinkTrichRut = loca.LinkTrichRut "
					+ " AND per.LinkTrichRut = time.LinkTrichRut WITH DISTINCT loca,time LIMIT "+num;
		if(GRAPH) q6+=" CREATE (loca)-[:DEN_THAM_VAO]->(time)";
		if(CSV) q6+=" return loca.DinhDanh AS start, time.DinhDanh AS end";
		
		String q7 ="MATCH (event:Event),(loca:Location) WHERE event.DiaDiem = loca.Nhan " + 
					" AND event.LinkTrichRut = loca.LinkTrichRut "
					+ " WITH DISTINCT event, loca LIMIT "+num+" ";
		if(GRAPH) q7+=" CREATE (event)-[:DIEN_RA_TAI]->(loca)";
		if(CSV) q7+=" return loca.DinhDanh AS start, event.DinhDanh AS end";
			
		String q8 ="MATCH (event:Event),(time:Time) WHERE " + 
					"  event.ThoiGianToChuc = time.Nhan"
					+ " WITH DISTINCT event, time LIMIT "+num;
		if(GRAPH) q8+=" CREATE (event)-[:DIEN_RA_VAO]->(time)";
		if(CSV) q8+=" return event.DinhDanh AS start, time.DinhDanh AS end";
					
		String q9 ="MATCH (org:Organization),(loca:Location) WHERE org.TruSo = loca.Nhan\r\n" + 
					"AND org.LinkTrichRut = loca.LinkTrichRut WITH DISTINCT org, loca LIMIT "+(num)+" ";
		if(GRAPH) q9+=" CREATE UNIQUE (org)-[:TRU_SO]->(loca)";
		if(CSV) q9+=" return loca.DinhDanh AS start, org.DinhDanh AS end";
			
		String q10 ="MATCH (loca:Location),(country:Country) WHERE loca.QuocGia = country.Nhan\r\n" + 
					"AND loca.LinkTrichRut = country.LinkTrichRut WITH DISTINCT loca, country LIMIT "+num;
		if(GRAPH) q10+=" CREATE (loca)-[:THUOC]->(country)";
		if(CSV) q10+=" return loca.DinhDanh AS start, country.DinhDanh AS end";
		
		q.add(q1);
		q.add(q2);
		q.add(q3);
		q.add(q4);
		q.add(q5);
		q.add(q6);
		q.add(q7);
		q.add(q8);
		q.add(q9);
		q.add(q10);
		
		return q.get(query-1);
	}
}
