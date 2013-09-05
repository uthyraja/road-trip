package nz.ac.waikato.cs.roadtrip.models;

import java.util.ArrayList;

public class Place {
	
	public String id;
	public String name;
	public Point location;
	public String reference;
	public String vicinity;
	public ArrayList<String> keywords = new ArrayList<String>();
	public Boolean open;
	public int rating;
	
	public Place(String id, String name, Point location, String reference, String vicinity, ArrayList<String> keywords, Boolean open, int rating){
		this.id = id;
		this.name = name;
		this.location = location;
		this.reference = reference;
		this.vicinity = vicinity;
		this.keywords = keywords;
		this.open = open;
		this.rating = rating;
	}
	
	public Place(){
		
	}
}
