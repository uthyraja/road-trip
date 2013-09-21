package nz.ac.waikato.cs.roadtrip.models;

public class Leg {
	Point start;
    Point end;
    String distance;
    String duration;
    
    public Leg(Point start, Point end, String distance, String duration){
    	this.distance= distance;
    	this.end=end;
    	this.start = start;
    	this.duration = duration;
    }
    
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
}
