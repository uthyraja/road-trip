package nz.ac.waikato.cs.roadtrip.helpers;

import android.app.Activity;
import android.content.Intent;

public class NavigationHelper {
	public static void goTo(Activity current, Class newActivity) throws Exception{
		Intent newPage = new Intent(current, newActivity);
		current.startActivity(newPage);
	}
}
