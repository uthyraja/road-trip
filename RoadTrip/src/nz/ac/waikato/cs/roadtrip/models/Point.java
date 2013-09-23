package nz.ac.waikato.cs.roadtrip.models;

import com.google.android.gms.maps.model.LatLng;

public class Point {
	private double lat = 0;
    private double lng = 0;

    public Point(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }
    
    public double getLatitude(){
    	return Double.valueOf(lat);
    }
    
    public double getLongitude(){
    	return Double.valueOf(lng);
    }

	public String getFormattedPoint() {
		return String.format("%s,%s", lat,lng);
	}

	public double distanceBetween(Point point) {
		double varible = lng - point.getLongitude();
		double distance = Math.sin(deg2rad(lat)) * Math.sin(deg2rad(point.getLatitude())) + Math.cos(deg2rad(lat)) * Math.cos(deg2rad(point.getLatitude())) * Math.cos(deg2rad(varible));
		
		distance = Math.acos(distance);
		distance = rad2deg(distance);
		distance = distance * 60 * 1.1515;
		distance = distance * 1.609344;
		
		return Math.abs(distance);
	}
	
	//converts radians to degrees
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    
    //converts degrees to radians 
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

	public LatLng toLatLng() {
		// TODO Auto-generated method stub
		return new LatLng(lat, lng);
	}
}
