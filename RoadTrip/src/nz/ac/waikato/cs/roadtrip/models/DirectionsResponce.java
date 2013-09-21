package nz.ac.waikato.cs.roadtrip.models;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class DirectionsResponce {
	
	public Route[] routes;
	
	public DirectionsResponce(Route[] routes){
		this.routes = routes;
	}
	
	public static class Route{
		public Bounds bounds;
		public String copyrights;
		public Leg[] legs;
		public OverViewPolyLine overview_polyline;
		
		public class Bounds{
			public Point northeast;
			public Point southwest;
		}
		
		public class Leg{
			public Item distance;
			public Item duration;
			public String end_address;
			public Point end_location;
			public String start_address;
			public Point start_location;
			public Step[] steps;
			
			public class Item{
				public String text;
				public int value;
			}
			
			public class Step{
				public Item distance;
				public Item duration;
				public Point end_location;
				public String html_instructions;
				public Point start_location;
			}

			public ArrayList<nz.ac.waikato.cs.roadtrip.models.Leg> stepsToList() {
				ArrayList<nz.ac.waikato.cs.roadtrip.models.Leg> list = new ArrayList<nz.ac.waikato.cs.roadtrip.models.Leg>();			
				for(Step s : steps){
					list.add(new nz.ac.waikato.cs.roadtrip.models.Leg(s.start_location, s.end_location, s.distance.text, s.duration.text));
				}
				return list;
			}
		}
		
		public class OverViewPolyLine{
			public String points;
			
			public ArrayList<LatLng> decodePoly() {
				 
		        ArrayList<LatLng> poly = new ArrayList<LatLng>();
		        int index = 0, len = points.length();
		        int lat = 0, lng = 0;
		 
		        while (index < len) {
		            int b, shift = 0, result = 0;
		            do {
		                b = points.charAt(index++) - 63;
		                result |= (b & 0x1f) << shift;
		                shift += 5;
		            } while (b >= 0x20);
		            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		            lat += dlat;
		 
		            shift = 0;
		            result = 0;
		            do {
		                b = points.charAt(index++) - 63;
		                result |= (b & 0x1f) << shift;
		                shift += 5;
		            } while (b >= 0x20);
		            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		            lng += dlng;
		 
		            LatLng p = new LatLng((((double) lat / 1E5)),
		                        (((double) lng / 1E5)));
		            poly.add(p);
		        }
		 
		        return poly;
		    }
		}
	}
}
