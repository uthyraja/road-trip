package nz.ac.waikato.cs.roadtrip;

import nz.ac.waikato.cs.roadtrip.controllers.MapsController;
import nz.ac.waikato.cs.roadtrip.helpers.MessageBoxHelper;
import nz.ac.waikato.cs.roadtrip.helpers.NavigationDrawerHelper;
import nz.ac.waikato.cs.roadtrip.helpers.NavigationHelper;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

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
			NavigationDrawerHelper.Initialise(this, R.id.left_drawer, R.array.maps_page_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps_page, menu);
		return true;
	}

}
