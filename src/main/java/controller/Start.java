package controller;

import java.util.Calendar; 
import deployment.Query;
public class Start {
	
	public static void main(String[] args) throws Exception {

		long begin = Calendar.getInstance().getTimeInMillis();
		
		//Tạo đỉnh và quan hệ	
//		BuildGraph Graph =new BuildGraph();
//		Graph.createGraph(100,200);
		
		BuildCSV CSV =new BuildCSV();
//		CSV.createNodeCSV(15000000);
//		CSV.createRelaCSV(17000000);
//		
		//Chạy các truy vấn
		Query run = new Query();
		run.Run();
		
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("Tổng Thời gian thực hiện: " + (end - begin)+" mili giây!");	
		Connection.connection.close();
				
	}
}