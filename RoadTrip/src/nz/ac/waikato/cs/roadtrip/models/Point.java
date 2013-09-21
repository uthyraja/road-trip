package nz.ac.waikato.cs.roadtrip.models;

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
}
