package nz.ac.waikato.cs.roadtrip.controllers;

import java.util.ArrayList;

import nz.ac.waikato.cs.roadtrip.models.Place;
import nz.ac.waikato.cs.roadtrip.models.Point;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlaceController {

	public static ArrayList<Place> newPlaceList(JSONObject mainObject) throws Exception{
		try{
			
			ArrayList<Place> placeList = new ArrayList<Place>();
			JSONArray results = mainObject.getJSONArray("results");
			
			for (int i = 0; i < results.length(); i++) {
				Place newPlace = new Place();
				
				JSONObject place = results.getJSONObject(i);
				newPlace.id = place.getString("id");
				newPlace.name = place.getString("name");
				newPlace.reference = place.getString("reference");
				newPlace.vicinity = place.getString("vicinity");
				
				//if they exist get..
				//newPlace.rating = place.getInt("rating");
				//newPlace.open = place.getJSONObject("opening_hours").getBoolean("open_now");
				
				newPlace.location = new Point(
						place.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
						place.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
				
				JSONArray keywords = place.getJSONArray("types");
				ArrayList<String> list = new ArrayList<String>();  
				
				if (keywords != null) { 
					for(int j = 0; i < keywords.length(); i++){
						list.add(keywords.get(j).toString());
					}
					newPlace.keywords = list;
				}
				placeList.add(newPlace);
			}
			return placeList;
		}
		 catch(Exception e){
			 throw new Exception("Error parsing json object: " + e.getMessage());
		 }
			
	}
}
