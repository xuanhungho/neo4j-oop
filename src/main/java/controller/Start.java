package controller;

import java.util.Calendar;
import deployment.Query;
public class Start {
	public static int num = 1000;
	
	public static void main(String[] args) {

//		System.out.println("Đang xoá các node cũ...");		
//		try {
//			Neo4j.connection.removeData();
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//		}
	
		long begin = Calendar.getInstance().getTimeInMillis();
		
		//Tạo đỉnh và quan hệ
		
		CreateEntity en =new CreateEntity();
		en.createEntitys(300, 400);
		
		//Chạy các truy vấn
		
		Query run = new Query();
		run.Run();
		
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("Tổng Thời gian thực hiện: " + (end - begin)+" mili giây!");	
		try {
			Connection.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
}
