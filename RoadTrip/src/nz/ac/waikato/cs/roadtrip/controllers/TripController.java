package nz.ac.waikato.cs.roadtrip.controllers;

import java.util.ArrayList;
import java.util.List;

import nz.ac.waikato.cs.roadtrip.MapsPage;
import nz.ac.waikato.cs.roadtrip.models.Leg;
import nz.ac.waikato.cs.roadtrip.models.Point;
import nz.ac.waikato.cs.roadtrip.models.Trip;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class TripController {

	public static Trip newTrip(JSONObject mainObject) throws Exception{
		try{
			Trip newTrip = new Trip();
			
			JSONArray root_ = mainObject.getJSONArray("routes");
			JSONObject root = root_.getJSONObject(0);
			
			JSONObject poly = root.getJSONObject("overview_polyline");
			//JSONObject poly = poly_.getJSONObject(0);
			
			List<LatLng> polyline = decodePoly(poly.getString("points"));
			newTrip.polylineOptions = new PolylineOptions()
				.width(10)
				.color(Color.argb(255, 30, 144, 255))
				.geodesic(true)
				.addAll(polyline);
			
			//get bounds for map view
			JSONObject bounds = root.getJSONObject("bounds");
			
			newTrip.northEast = new Point(
					bounds.getJSONObject("northeast").getDouble("lat"),
					bounds.getJSONObject("northeast").getDouble("lng"));
			
			newTrip.southWest = new Point(
					bounds.getJSONObject("southwest").getDouble("lat"),
					bounds.getJSONObject("southwest").getDouble("lng"));
			
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
	
	private static List<LatLng> decodePoly(String encoded) {
		 
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
 
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
 
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
 
            LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
            poly.add(p);
        }
 
        return poly;
    }
}
