package nz.ac.waikato.cs.roadtrip.helpers;

import nz.ac.waikato.cs.roadtrip.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MessageBoxHelper {
	public static void showMessageBox(Context current, String title, String message){
		AlertDialog ad = new AlertDialog.Builder(current).create();
		ad.setCancelable(false); // This blocks the 'BACK' button
		ad.setMessage(message);
		ad.setTitle(title);
		
		ad.setButton(DialogInterface.BUTTON_NEUTRAL, current.getString(R.string.ok), new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.dismiss();                    
		    }
		});
		
		ad.show();
	}
	
	public static void showMessageBox(Context current, String message){
		showMessageBox(current, current.getString(R.string.default_messageBox_title), message);
	}
}
