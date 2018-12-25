package controller;
import java.util.Calendar;
import java.util.Random;

import data.Data;

public class RandomPropertiesTime extends RandomPropetiesNode{

	public RandomPropertiesTime() {
		// TODO Auto-generated constructor stub
	}
	
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
	public String randomDinhDanh(int i) {
		Calendar calendar = Calendar.getInstance();
		i = i%6500+new Random().nextInt(15);
		calendar.add(Calendar.DATE, -i);
		String a =  calendar.getTime().toString();
		Tempid = a.substring(0,11)+" "+a.substring(24,28);
		return Tempid.replace(" ","_")+i;
	}
	
}
