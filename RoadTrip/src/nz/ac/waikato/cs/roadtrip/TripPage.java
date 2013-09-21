package nz.ac.waikato.cs.roadtrip;

import nz.ac.waikato.cs.roadtrip.helpers.MessageBoxHelper;
import nz.ac.waikato.cs.roadtrip.models.SerializableTrip;
import nz.ac.waikato.cs.roadtrip.models.Trip;
import nz.ac.waikato.cs.roadtrip.models.TripCategories;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v4.app.NavUtils;

public class TripPage extends Activity {

	SerializableTrip newTrip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip_page);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Bundle b = this.getIntent().getExtras();
		if(b!=null)
		    newTrip = (SerializableTrip) b.getSerializable("trip");
		
		setOriginEditText();
	}

	private void setOriginEditText() {
		// TODO Auto-generated method stub
		if(newTrip.hasStart()){	
			EditText origin = (EditText)findViewById(R.id.editTextOrigin);
			if(origin != null )
				origin.setText("Current Location");
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trip_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void buttonGoClick(View view) {
		if(allControlValuesAreNotNullAndValid()){
			EditText origin = (EditText)findViewById(R.id.editTextOrigin);
			EditText destination = (EditText)findViewById(R.id.editTextDestination);
			
			CheckBox cbAcc = (CheckBox)findViewById(R.id.cbAccomodation);
			CheckBox cbEnt = (CheckBox)findViewById(R.id.cbEntertainment);
			CheckBox cbFood = (CheckBox)findViewById(R.id.cbFood);
			CheckBox cbLand = (CheckBox)findViewById(R.id.cbLandmarks);
			CheckBox cbWalks = (CheckBox)findViewById(R.id.cbWalks);
			
			Spinner spin = (Spinner)findViewById(R.id.spinnerRadius);
			
			newTrip.tripCategories = new TripCategories(cbAcc.isChecked(), cbEnt.isChecked(), cbFood.isChecked(), cbLand.isChecked(), cbWalks.isChecked());
			newTrip.setEnd(destination.getText().toString() + ", New Zealand");
			newTrip.setRaduis(getRadiusFromId(spin.getSelectedItemId()));
			
			Intent result = new Intent();
			
			if(!origin.getText().toString().equalsIgnoreCase("Current Location")){
				newTrip.setStart(origin.getText().toString());
			}
			
			result.putExtra("trip", newTrip);
			setResult(RESULT_OK, result);
			
			try{
				finish();
			}
			catch(Exception e){
				MessageBoxHelper.showMessageBox(this, e.getMessage());
			}
		}
		else
			MessageBoxHelper.showMessageBox(this, "Incorrect Inputs", "One or more of the input are incorrect");
	}

	private double getRadiusFromId(long selectedItemId) {
		return (selectedItemId + 1) * 500;
	}

	private boolean allControlValuesAreNotNullAndValid() {
		// TODO Auto-generated method stub
		return true;
	}
}
