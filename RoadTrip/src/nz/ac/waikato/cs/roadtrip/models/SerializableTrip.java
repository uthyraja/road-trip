package nz.ac.waikato.cs.roadtrip.models;

import java.io.Serializable;

//used to pass between maps page and trip page
	public class SerializableTrip implements Serializable{

		private static final long serialVersionUID = 1L;
		private String start;
		private String end;
		private double radius;
		public TripCategories tripCategories;
		
		public SerializableTrip(String start, String end, double radius){
			this.start = start;
			this.end = end;
			this.radius = radius;
		}
		
		public boolean hasStart(){
			return start != null;
		}

		public void setEnd(String string) {
			end = string;
			
		}

		public void setRaduis(double radiusFromId) {
			radius = radiusFromId;
			
		}

		public void setStart(String string) {
			start = string;
			
		}

		public String getStart() {
			return start;
		}

		public String getEnd() {
			return end;
		}

		public double getRadius() {
			// TODO Auto-generated method stub
			return radius;
		}

		public TripCategories getCategories() {
			// TODO Auto-generated method stub
			return tripCategories;
		}

		public void setTripCategories(TripCategories placeTypes) {
			tripCategories = placeTypes;
		}
	}