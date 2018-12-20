package controller;

import java.util.Calendar;
import deployment.Query;
public class Start {
	public static int num = 1000;
	
	public static void main(String[] args) {

//		System.out.println("Đang xoá các node cũ...");		
//		Connection.connection.removeData();
	
		long begin = Calendar.getInstance().getTimeInMillis();
		
		//Tạo đỉnh và quan hệ
		
//		CreateEntity en =new CreateEntity();
//		en.createEntitys(300, 400);
		
		//Chạy các truy vấn
//		Query run = new Query();
//		run.Run();
		
		String q = "MATCH (e:Person) WHERE e.Nhan CONTAINS {Ho} OR e.Age CONTAINS {Ho} OR e.Quoctich CONTAINS {Ho} OR e.Mota CONTAINS {Ho} "
    			+ "RETURN e.Nhan AS Nhan, e.Age AS Age, e.Mota AS Mota, e.Job AS Job, e.Quoctich AS qt";
		
		
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("Tổng Thời gian thực hiện: " + (end - begin)+" mili giây!");	
		try {
			Connection.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
}
