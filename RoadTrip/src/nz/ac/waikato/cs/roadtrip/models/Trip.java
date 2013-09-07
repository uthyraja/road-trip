package nz.ac.waikato.cs.roadtrip.models;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class Trip {
	public Point start;
    public Point end;
    public String distance;
    public String duration;
    public PolylineOptions polylineOptions;
    
    public Point northEast;
    public Point southWest;
    
    public ArrayList<Leg> legs = new ArrayList<Leg>();
    
    public ArrayList<LatLng> points;
    
    public ArrayList<LatLng> trimedPoints = new ArrayList<LatLng>();

    public Trip(Point start, Point end, String distance, String duration, ArrayList<Leg> pitstops){
        this.start = start;
        this.end = end;
        this.legs = pitstops;
        this.distance = distance;
        this.duration = duration;
    }

	public Trip() {
		// TODO Auto-generated constructor stub
	}
	
	//reduces the size of points to contain a point at a set distance
	public ArrayList<LatLng> getTrimmedPoints(int radius){
		LatLng currentPoint = points.get(0);
		trimedPoints.add(points.get(0));
		
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
    
}
