package nz.ac.waikato.cs.roadtrip;

import nz.ac.waikato.cs.roadtrip.controllers.MapsController;
import nz.ac.waikato.cs.roadtrip.helpers.MessageBoxHelper;
import nz.ac.waikato.cs.roadtrip.helpers.NavigationDrawerHelper;
import nz.ac.waikato.cs.roadtrip.listeners.DrawerItemClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MapsPage extends Activity {

	MapsController map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_page);
		
		try {
			initialiseComponents();
		} catch (Exception e) {
			MessageBoxHelper.showMessageBox(this, e.getMessage());
		}
	}

	private void initialiseComponents() throws Exception {
			map = new MapsController(this);
			
			//later i will put this method in the navigationdrawerhelper class
			//NavigationDrawerHelper.Initialise((ListView) findViewById(R.id.left_drawer), getResources().getStringArray(R.menu.maps_page));
			initialiseDrawer();
	}

	private void initialiseDrawer() {
		//mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
        String[] list = new String[]{
        	"Settings",
        	"About"
        };
        
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps_page, menu);
		return true;
	}

}
