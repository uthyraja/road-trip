package nz.ac.waikato.cs.roadtrip.models;

import java.util.ArrayList;

public class DetailedPlace extends Place {
	public String icon_url;
	public int price_level;
	public String address;
	
	public DetailedPlace(String icon_url, int price_level, String address){
		this.icon_url = icon_url;
		this.price_level = price_level;
		this.address = address;
	}
	
	// Pull details from google
	public void getMoreDetails(){
		
	}
	
	//make into JSON string
	
	//return the JSON string
}
