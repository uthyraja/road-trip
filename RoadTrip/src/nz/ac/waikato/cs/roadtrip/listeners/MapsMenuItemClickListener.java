package nz.ac.waikato.cs.roadtrip.listeners;

import java.util.ArrayList;

import nz.ac.waikato.cs.roadtrip.factories.ActivityFactory;
import nz.ac.waikato.cs.roadtrip.helpers.MessageBoxHelper;
import nz.ac.waikato.cs.roadtrip.helpers.NavigationHelper;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MapsMenuItemClickListener implements OnItemClickListener {

	Activity current;
	
	public MapsMenuItemClickListener(Activity current) {
		this.current = current;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		try{
			ArrayList<Class> list = ActivityFactory.mapsPageNavigation();
			Class newActivity = list.get(arg2);
		
			NavigationHelper.goTo(current, newActivity);
		}
		catch(Exception e){
			MessageBoxHelper.showMessageBox(current, e.getMessage());
		}
	}

}
