package nz.ac.waikato.cs.roadtrip.controllers;

import nz.ac.waikato.cs.roadtrip.MapsPage;
import nz.ac.waikato.cs.roadtrip.asyncTasks.HttpRequestAsync;

public class GoogleDirectionsConnection {
	public static void getDirections(String origin, String destination) throws Exception{
		new HttpRequestAsync().execute(origin, destination);
	}
}
