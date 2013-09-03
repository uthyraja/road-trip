package nz.ac.waikato.cs.roadtrip.models;

public class Leg {
	Point start;
    Point end;
    String distance;
    String duration;
    String polyline;
    
    
	public void setStart(Point point) {
		start = point;
		
	}
	public void setEnd(Point point) {
		end = point;
		
	}
	public void setDistance(String string) {
		distance = string;
		
	}
	public void setDuration(String string) {
		duration = string;
		
	}
	public void setPolyline(String string) {
		polyline = string;
		
	}
}
