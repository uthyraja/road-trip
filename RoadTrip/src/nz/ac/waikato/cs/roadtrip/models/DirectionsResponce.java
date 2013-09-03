package nz.ac.waikato.cs.roadtrip.models;

import java.util.List;

public class DirectionsResponce {
	public static class Root{
		@Key("routes")
		public List<Route> route;
	}
	
	public static class Route{
		@Key("overview_polyline")
		public OverviewPolyline overviewPolyline;
		
		@Key("legs")
		public Legs legs;
	}
	
	public static class OverviewPolyline{
		
	}
	
	public static class Legs{
		
	}
}
