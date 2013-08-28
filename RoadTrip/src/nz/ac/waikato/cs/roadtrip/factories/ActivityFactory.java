package nz.ac.waikato.cs.roadtrip.factories;

import java.util.ArrayList;

import nz.ac.waikato.cs.roadtrip.SettingsActivity;
import nz.ac.waikato.cs.roadtrip.SettingsPage;
import nz.ac.waikato.cs.roadtrip.TripPage;

public class ActivityFactory {

	public static ArrayList<Class> mapsPageNavigation() {
		ArrayList<Class> list = new ArrayList<Class>();
		
		list.add(TripPage.class);
		list.add(SettingsPage.class);
		
		return list;
	}

}
