package nz.ac.waikato.cs.roadtrip.helpers;

import nz.ac.waikato.cs.roadtrip.listeners.DrawerItemClickListener;
import nz.ac.waikato.cs.roadtrip.listeners.MapsMenuItemClickListener;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NavigationDrawerHelper {

	public static void Initialise(Activity current, int listView, int array) {
		ListView mDrawerList = (ListView) current.findViewById(listView);
        String[] list = current.getResources().getStringArray(array);
        
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(current, android.R.layout.simple_list_item_1, list));
        
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new MapsMenuItemClickListener(current));
	}
}
