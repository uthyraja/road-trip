package nz.ac.waikato.cs.roadtrip.models;

import java.util.ArrayList;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

public class Trip{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Point start;
    public Point end;
    public String distance;
    public String duration;
    public PolylineOptions polylineOptions;
    public Point northEast;
    public Point southWest;
    public TripCategories tripCategories;
    public double radius;
    
    public ArrayList<Leg> legs = new ArrayList<Leg>();
    
    public ArrayList<Place> pitStops = new ArrayList<Place>();
    
    public ArrayList<LatLng> points;
    
    public ArrayList<LatLng> trimedPoints = new ArrayList<LatLng>();
	public String end_address;
	public String start_address;

    public Trip(Point start, Point end){
        this.start = start;
        this.end = end;
    }

	public Trip() {
		// TODO Auto-generated constructor stub
	}
	
	public Trip(SerializableTrip t) {
		this.start_address = t.getStart();
		this.end_address = t.getEnd();
		this.radius = t.getRadius();
	}

	//reduces the size of points to contain a point at a set distance
	public ArrayList<LatLng> getTrimmedPoints(int radius){
		LatLng currentPoint = points.get(0);
		trimedPoints.add(points.get(0));
		
		/* only checks every third point to try save time - didnt seem to save much time so keep old loop
		 * for(int i = 0; i < points.size(); i=i+3){
			LatLng point = points.get(i);
			if(calcDistance(currentPoint, point) >= radius){
				trimedPoints.add(point);
				currentPoint = point;
			}
		}*/
		
		for(LatLng point : points){
			if(calcDistance(currentPoint, point) >= radius){
				trimedPoints.add(point);
				currentPoint = point;
			}
		}
		return trimedPoints;
	}
	
	//calculates the distance between two giving latlng points in kilometers
	public double calcDistance(LatLng a, LatLng b){
		double varible = a.longitude - b.longitude;
		double distance = Math.sin(deg2rad(a.latitude)) * Math.sin(deg2rad(b.latitude)) + Math.cos(deg2rad(a.latitude)) * Math.cos(deg2rad(b.latitude)) * Math.cos(deg2rad(varible));
		distance = Math.acos(distance);
		distance = rad2deg(distance);
		distance = distance * 60 * 1.1515;
		distance = distance * 1.609344;
		
		return distance;
	}
	
	//converts radians to degrees
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    
    //converts degrees to radians 
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

	public boolean hasStart() {
		return start != null;
	}
    
	public SerializableTrip getSerializable() {
		if(hasStart())
			return new SerializableTrip("Current Location", end_address, radius);
		
		else
			return new SerializableTrip(start_address, end_address, radius);
	}

	public String getGoogleMapsAPIHttpRequest() {
		
		String baseURI = "http://maps.googleapis.com/maps/api/directions/json?";
		String parameters;
		String allPitStops = ""; 
		
		if(pitStops.size() != 0){

			for(Place place : pitStops){
				allPitStops = allPitStops + place.location.getFormattedPoint() + "%7C";
			}
			allPitStops = allPitStops.substring(0, allPitStops.length() - 3);
			
			parameters = String.format("origin=%s&destination=%s&waypoints=optimize:true", this.start.getFormattedPoint(),this.end_address);
			parameters = parameters + "%7C" + allPitStops + "&sensor=true";
		}
		else{
			if(start_address == null || start_address.equalsIgnoreCase("current Location"))
				parameters = String.format("origin=%s&destination=%s&sensor=true", this.start.getFormattedPoint(), this.end_address);
			else 
				parameters = String.format("origin=%s&destination=%s&sensor=true", this.start_address, this.end_address);
		}
		String fullURI = (baseURI + parameters).replace(" ", "%20");
		
		return fullURI;
	}

	public void analizeJSON(String mainObject) throws Exception{
		Gson gson = new Gson();
		DirectionsResponce responce = gson.fromJson(mainObject.toString(), DirectionsResponce.class);
		
		this.distance = responce.routes[0].legs[0].distance.text;
		this.duration = responce.routes[0].legs[0].duration.text;
		this.northEast = responce.routes[0].bounds.northeast;
		this.southWest = responce.routes[0].bounds.southwest;
		this.legs = responce.routes[0].legs[0].stepsToList();
		this.points = responce.routes[0].overview_polyline.decodePoly();
		this.end = responce.routes[0].legs[0].end_location;
		this.start = responce.routes[0].legs[0].start_location;
		this.start_address = responce.routes[0].legs[0].start_address;
		this.end_address = responce.routes[0].legs[0].end_address;
		
		this.polylineOptions = new PolylineOptions()
        	.width(10)
        	.color(Color.argb(255, 30, 144, 255))
        	.geodesic(true)
        	.addAll(this.points);
		
	}

	public void deserializeTrip(SerializableTrip st) {
		this.start_address = st.getStart();
		this.end_address = st.getEnd();
		this.radius = st.getRadius();
		this.tripCategories = st.getCategories();
	}
}
