package nz.ac.waikato.cs.roadtrip.models;

import java.util.ArrayList;

public class Trip {
	public Point start;
    public Point end;
    public String distance;
    public String duration;
    
    public ArrayList<Leg> legs = new ArrayList<Leg>();

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
}
