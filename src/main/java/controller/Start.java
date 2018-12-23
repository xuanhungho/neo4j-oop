package controller;

import java.io.IOException;
import java.util.Calendar; 
import deployment.Query;
public class Start {
	
	public static void main(String[] args) throws IOException {

//		System.out.println("Đang xoá các node cũ...");		
//		Connection.connection.removeData();
	
		long begin = Calendar.getInstance().getTimeInMillis();
		
		//Tạo đỉnh và quan hệ
		
		BuildGraph en =new BuildGraph();
		en.createGraph(600, 400);
		
		//Chạy các truy vấn
//		Query run = new Query();
//		run.Run();
		
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("Tổng Thời gian thực hiện: " + (end - begin)+" mili giây!");	
		try {
			Connection.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
}












//CreatePerson per = new CreatePerson();
//per.saveCSV(20);
//
//String loadCSV="LOAD CSV WITH HEADERS FROM 'Person.csv' AS line " + 
//		"CREATE (:Person { Nhan: line.Nhan})";
//Connection.connection.execute(loadCSV);