package nz.ac.waikato.cs.roadtrip.models;

import java.util.ArrayList;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

public class Trip{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point start;
    private Point end;
    private String distance;
    private String duration;
    private PolylineOptions polylineOptions;
    private Point northEast;
    private Point southWest;
    private TripCategories tripCategories;
    private double radius;
    
    private ArrayList<Leg> legs = new ArrayList<Leg>();
    
    private ArrayList<Place> pitStops = new ArrayList<Place>();
    
    private ArrayList<Point> points;
    
    private String end_address;
    private String start_address;

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
	public ArrayList<Point> getTrimmedPoints(){
		
		Point currentPoint = points.get(0);
	    ArrayList<Point> trimedPoints = new ArrayList<Point>();
		trimedPoints.add(currentPoint);
		
		for(Point point : points){
			if(currentPoint.distanceBetween(point) >= radius){
				trimedPoints.add(point);
				currentPoint = point;
			}
		}
		
		return trimedPoints;
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
		String parameters = String.format("%s%s%s&sensor=false", getOriginUri(), getDestinationUri(), getWayPointsUri());
		String fullURI = (baseURI + parameters).replace(" ", "%20");
		
		return fullURI;
	}

	private String getWayPointsUri() {
		if(pitStops == null || pitStops.size() == 0)
			return "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("&waypoints=optimize:false%7c");
		
		for(int i = 0; i < pitStops.size(); i++){
			sb.append(pitStops.get(i).location.getFormattedPoint());
			if(i != pitStops.size() - 1)
				sb.append("%7c");
		}
		
		return sb.toString();
	}

	private String getDestinationUri() {
		// TODO Auto-generated method stub
		return String.format("&destination=%s", end_address);
	}

	private String getOriginUri() {
		if(start_address == null || start_address.equalsIgnoreCase("current Location"))
			return String.format("origin=%s", start.getFormattedPoint());
		return String.format("origin=%s", start_address);
	}

	public void analizeJSON(String mainObject) throws Exception{
		Gson gson = new Gson();
		DirectionsResponce responce = gson.fromJson(mainObject.toString(), DirectionsResponce.class);
		
		this.distance = responce.routes[0].legs[0].distance.text;
		this.duration = responce.routes[0].legs[0].duration.text;
		this.northEast = responce.routes[0].bounds.northeast;
		this.southWest = responce.routes[0].bounds.southwest;
		this.legs = responce.routes[0].legs[0].stepsToList();
		this.end = responce.routes[0].legs[0].end_location;
		this.start = responce.routes[0].legs[0].start_location;
		this.start_address = responce.routes[0].legs[0].start_address;
		this.end_address = responce.routes[0].legs[0].end_address;
		
		ArrayList<LatLng> llList = responce.routes[0].overview_polyline.decodePoly();
		
		this.points = latLngToPoints(llList);
		this.polylineOptions = new PolylineOptions()
        	.width(10)
        	.color(Color.argb(255, 30, 144, 255))
        	.geodesic(true)
        	.addAll(llList);
	}

	private ArrayList<Point> latLngToPoints(ArrayList<LatLng> decodePoly) {
		ArrayList<Point> pList = new ArrayList<Point>();
		
		for(LatLng ll : decodePoly)
			pList.add(new Point(ll.latitude, ll.longitude));
		
		return pList;
	}

	public void deserializeTrip(SerializableTrip st) {
		this.start_address = st.getStart();
		this.end_address = st.getEnd();
		this.radius = st.getRadius();
		this.tripCategories = st.getCategories();
	}

	public ArrayList<String> getGooglePlacesHttpRequests() {

		String baseUri = "https://maps.googleapis.com/maps/api/place/search/json?";
		String apiKey = "AIzaSyBtffHwqCLotld7p15WG8JcGXkrVzratL4";
		ArrayList<String> requests = new ArrayList<String>();
		
		for (Point location : getTrimmedPoints()){
			String request = String.format("%slocation=%s&radius=%s&sensor=false&key=%s%s", baseUri, location.getFormattedPoint(), radius, apiKey, tripCategories.getParamURI());
			requests.add(request.replace(" ", "%20"));
		}
		
		return requests;
	}

	public void addPitstop(Place selected) {
		pitStops.add(selected);
	}

	public void setStart(Point currentPossition) {
		this.start = currentPossition;
	}

	public void setEndAddress(String string) {
		this.end_address = string;
	}

	public void setRaduis(int i) {
		this.radius = i;
	}

	public void setCategories(TripCategories tripCategories) {
		this.tripCategories = tripCategories;
	}

	public PolylineOptions GetPolylineOptions() {
		// TODO Auto-generated method stub
		return polylineOptions;
	}

	public Point getStart() {
		// TODO Auto-generated method stub
		return start;
	}

	public Point getEnd() {
		// TODO Auto-generated method stub
		return end;
	}

	public ArrayList<Place> getPitStops() {
		// TODO Auto-generated method stub
		return pitStops;
	}

	public LatLngBounds getMapBounds() {
		// TODO Auto-generated method stub
		return new LatLngBounds(southWest.toLatLng(), northEast.toLatLng());
	}
}
	