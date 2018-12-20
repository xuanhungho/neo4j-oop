package controller;

import java.util.Calendar;  
import java.util.Random;

import data.Data;
import model.Time;

public class CreateTime implements ICreate{
	private String Tempid;
	
	Data data = new Data();

	@Override
	public String randomNhan() {
		return Tempid;
	}

	@Override
	public String randomMoTa() {
		return Tempid;
	}
	
	@Override
	public String randomLink() {
		String link ;
		link = data.link[new Random().nextInt(data.link.length)];
		return link;
	}
	
	@Override
	public String randomThoiGian(int i) {
		Calendar calendar = Calendar.getInstance();
		i = i%6500+new Random().nextInt(15);
		calendar.add(Calendar.DATE, -i);
		String a =  calendar.getTime().toString();
		return a.substring(0,11)+" "+a.substring(24,28);
	}
	
	@Override
	public String randomDinhDanh(int i) {
		Calendar calendar = Calendar.getInstance();
		i = i%6500;
		calendar.add(Calendar.DATE, -i);
		String a =  calendar.getTime().toString();
		Tempid = a.substring(0,11)+" "+a.substring(24,28);
		return Tempid.replace(" ","_")+i;
	}
	
	@Override
	public void CreateNode(int num) {
		long begin = Calendar.getInstance().getTimeInMillis();
		Time Time = new Time();

		try {
			System.out.println("Đang thêm node Time...");
				for(int i=0; i<num; i++) {	
				Time.setNhan(randomNhan());
				Time.setDinhdanh(randomDinhDanh(i));
				Time.setMota(randomMoTa());
				Time.setLink(randomLink());
				Time.setDate(randomThoiGian(i));

				Connection.connection.execute("CREATE ("+Time.getDinhdanh()+":Time { "
					+ "DinhDanh: '"+Time.getDinhdanh()+"', "
					+ "Nhan: '"+Time.getNhan()+"', "
					+ "Mota: '"+Time.getMota()+"', "
					+ "LinkTrichRut: '"+Time.getLink()+"', "			
					+ "ThoiGianTrichRut: '"+Time.getDate()+"'})");
//				System.out.println("Da them "+Time.getDinhdanh()+"!");
		}	
		
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("Thời gian thực hiện: " + (end - begin)+" mili giây!");
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}
}
