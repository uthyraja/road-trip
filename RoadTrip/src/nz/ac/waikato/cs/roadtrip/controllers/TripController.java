package nz.ac.waikato.cs.roadtrip.controllers;

import java.util.ArrayList;

import nz.ac.waikato.cs.roadtrip.MapsPage;
import nz.ac.waikato.cs.roadtrip.models.Leg;
import nz.ac.waikato.cs.roadtrip.models.Point;
import nz.ac.waikato.cs.roadtrip.models.Trip;

import org.json.JSONArray;
import org.json.JSONObject;

public class TripController {

	public static Trip newTrip(JSONObject mainObject) throws Exception{
		try{
			Trip newTrip = new Trip();
			
			JSONArray root_ = mainObject.getJSONArray("routes");
			JSONObject root = root_.getJSONObject(0);
			
			JSONArray route_ = root.getJSONArray("legs");
			JSONObject route = route_.getJSONObject(0);
			
			//set total trips distance and time
			//newTrip.distance = route.getJSONObject("distance").getString("text");
			//newTrip.duration = route.getJSONObject("duration").getString("text");
			newTrip.distance = route.getJSONObject("distance").getString("text");
			newTrip.duration = route.getJSONObject("duration").getString("text");
			
			//set the start possition
			newTrip.start = new Point(
					route.getJSONObject("start_location").getDouble("lat"),
					route.getJSONObject("start_location").getDouble("lng"));
			
			//set the end possition
			newTrip.end = new Point(
					route.getJSONObject("end_location").getDouble("lat"),
					route.getJSONObject("end_location").getDouble("lng"));
			
			JSONArray legs = route.getJSONArray("steps");
			
			//create the list of pitstops
			ArrayList<Leg> pitstops = new ArrayList<Leg>();
			
			//for each leg of the trip, add it to the list
			for(int i = 0; i < legs.length(); i++){
				JSONObject thisLeg_raw = legs.getJSONObject(i);
				
				JSONObject _start = thisLeg_raw.getJSONObject("start_location");
				JSONObject _end = thisLeg_raw.getJSONObject("end_location");
				JSONObject _distance = thisLeg_raw.getJSONObject("distance");
				JSONObject _duration = thisLeg_raw.getJSONObject("duration");
				JSONObject _polyline = thisLeg_raw.getJSONObject("polyline");
				
				Leg thisLeg = new Leg();
				
				thisLeg.setStart(new Point(_start.getDouble("lat"), _start.getDouble("lng")));
				thisLeg.setEnd( new Point(_end.getDouble("lat"), _end.getDouble("lng")));
				thisLeg.setDistance( _distance.getString("text"));
				thisLeg.setDuration(_duration.getString("text"));
				thisLeg.setPolyline(_polyline.getString("points"));
				
				pitstops.add(thisLeg);
			}
			
			newTrip.legs = pitstops;
			return newTrip;
		}
		 catch(Exception e){
			 throw new Exception("Error parsing json object: " + e.getMessage());
		 }
	}

	public static void drawTrip(Trip result, MapsPage mPage) {
		
	}
}
