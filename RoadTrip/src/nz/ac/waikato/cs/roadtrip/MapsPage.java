package nz.ac.waikato.cs.roadtrip;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpRequestFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.android.gms.maps.model.LatLng;

import nz.ac.waikato.cs.roadtrip.asyncTasks.HttpRequestAsync;
import nz.ac.waikato.cs.roadtrip.controllers.GoogleDirectionsConnection;
import nz.ac.waikato.cs.roadtrip.controllers.MapsController;
import nz.ac.waikato.cs.roadtrip.controllers.PlaceController;
import nz.ac.waikato.cs.roadtrip.controllers.TripController;
import nz.ac.waikato.cs.roadtrip.factories.ActivityFactory;
import nz.ac.waikato.cs.roadtrip.helpers.MessageBoxHelper;
import nz.ac.waikato.cs.roadtrip.helpers.NavigationDrawerHelper;
import nz.ac.waikato.cs.roadtrip.helpers.NavigationHelper;
import nz.ac.waikato.cs.roadtrip.listeners.MapsMenuItemClickListener;
import nz.ac.waikato.cs.roadtrip.models.DirectionsResponce;
import nz.ac.waikato.cs.roadtrip.models.Place;
import nz.ac.waikato.cs.roadtrip.models.Point;
import nz.ac.waikato.cs.roadtrip.models.SerializableTrip;
import nz.ac.waikato.cs.roadtrip.models.Trip;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MapsPage extends Activity {

	MapsController map;
	MapsPage mPage;
	Trip trip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_page);
		
		try {
			initialiseComponents();
		} catch (Exception e) {
			MessageBoxHelper.showMessageBox(this, e.getMessage());
		}
		
		EditText textMessage = (EditText)findViewById(R.id.text_box_search);
	    textMessage.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
					EditText textMessage = (EditText)v;
					
					try {
						hideKeyboard();
						Point p = map.getCurrentPossition();
						//GoogleDirectionsConnection.getDirections(p.getLatitude() + "," + p.getLongitude(), textMessage.getText().toString());
						new HttpRequestAsync().execute(p.getLatitude() + "," + p.getLongitude(), textMessage.getText().toString() + ", New Zealand");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.err.println(e.getMessage());
					}
				}
				
				return true;
			}
	    }); 
	    
	    
	}

	protected void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(((EditText)findViewById(R.id.text_box_search)).getWindowToken(), 0);
	}

	private void initialiseComponents() throws Exception {
			map = new MapsController(this);
			mPage = this;
			
			
			//NavigationDrawerHelper.Initialise(this, R.id.left_drawer, R.array.maps_page_menu);
			ListView mDrawerList = (ListView) this.findViewById(R.id.left_drawer);
	        String[] list = this.getResources().getStringArray(R.array.maps_page_menu);
	        
	        // Set the adapter for the list view
	        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
	        
	        // Set the list's click listener
	        mDrawerList.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					try{
						ArrayList<Class> list = ActivityFactory.mapsPageNavigation();
						Class newActivity = list.get(arg2);
						
						//NavigationHelper.goTo(current, newActivity);
						
						trip = new Trip(map.getCurrentPossition(), null, null, null, null);
						
						Intent intent = new Intent(mPage, newActivity);
						intent.putExtra("trip", trip.getSerializable());
						startActivityForResult(intent, arg2);
					}
					catch(Exception e){
						MessageBoxHelper.showMessageBox(mPage, e.getMessage());
					}
				}
	        	
	        });
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	     if (requestCode == 0)
	     {
	         if (resultCode == RESULT_OK)
	         {
	        	 SerializableTrip st = (SerializableTrip) intent.getSerializableExtra("trip");
	             trip = new Trip(st);
	             // Do whatever with the updated object
	         }
	     }
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		try{
		getMenuInflater().inflate(R.menu.maps_page, menu);
		}
		catch(Exception e){
			MessageBoxHelper.showMessageBox(this, e.getMessage());
		}
		return true;
	}
	
	public void displayPlaceList(HashMap<String, Place> finalPlaces){
		/*
		// DUMMY DATA
		Point wel = new Point(-37.79514,175.295434);
		Point lel = new Point(-37.89814,175.565434);
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("Fast Food");
		keywords.add("Eat");		
		
		Place p1 = new Place ("ID1", "McDaniels", wel, "reference", "vicinity", keywords, true, 2);
		Place p2 = new Place("ID2", "Burger Queen", lel, "reference2", "vicinity2", keywords, false, 4);
		Place p3 = new Place("ID3", "Carls Snr.", lel, "reference3", "vicinity3", keywords, false, 3);
		
		ArrayList<Place> list = new ArrayList<Place>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		// END OF DUMMY DATA
		*/
		
		ArrayList<Place> placeNames = new ArrayList<Place>();
		for(Place p : finalPlaces.values()){
			placeNames.add(p);
		}
				
		ListView lv = (ListView)findViewById(R.id.right_drawer);
		ArrayAdapter<Place> arrayAdapter = new ArrayAdapter<Place>(this,android.R.layout.simple_list_item_1, placeNames);
		lv.setAdapter(arrayAdapter); 
	}
	
	private class HttpRequestAsync extends AsyncTask<String, String, Trip>{

	    @Override
	    protected Trip doInBackground(String... uri) {
	    	try{
				String baseURI = "http://maps.googleapis.com/maps/api/directions/json?";
				String parameters = String.format("origin=%s&destination=%s&sensor=true", uri[0], uri[1]);
				String fullURI = (baseURI + parameters).replace(" ", "%20");
				
				HttpGet connection = new HttpGet(fullURI);
				HttpClient client = new DefaultHttpClient();
		        HttpResponse response = client.execute(connection);
		        int res = response.getStatusLine().getStatusCode();
		        
		        if(res == HttpStatus.SC_OK){
		        	//DirectionsResponce dr = response.parseAs(DirectionsResponce.class);		        	
		        	JSONObject mainObject = new JSONObject(convertStreamToString(response.getEntity().getContent()));
		        	Trip thisTrip = TripController.newTrip(mainObject);
		        	return thisTrip;
		        }
			}
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	    	
	    	return null;
	    }
	    
	    private String convertStreamToString(java.io.InputStream is) {
	        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	        return s.hasNext() ? s.next() : "";
	    }

	    @SuppressWarnings("unchecked")
		@Override
	    protected void onPostExecute(Trip result) {
	        super.onPostExecute(result);
	        
	        if(result != null){
	        	map.drawTrip(result);
	        	new GetPlacesAsync().execute(result.getTrimmedPoints(1));
	        }
	    }
	}
	private class GetPlacesAsync extends AsyncTask<ArrayList<LatLng>, String, HashMap<String,Place>>{

		private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
		private static final String TYPE_SEARCH = "/search";
		private static final String OUT_JSON = "/json";
		private static final String API_KEY = "AIzaSyBtffHwqCLotld7p15WG8JcGXkrVzratL4";
		private double radius = 1000;
		
		@Override
		protected HashMap<String,Place> doInBackground(ArrayList<LatLng>... arg0) {

			HashMap<String, Place> finalPlaces = new HashMap<String, Place>();
			ArrayList<Place> places;
			
			for (ArrayList<LatLng> listLocation : arg0) {
				
				for (LatLng location : listLocation){
					
					//build url
					StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_SEARCH + OUT_JSON);
			        sb.append("?location=" + location.latitude + "," + location.longitude + "&radius=" + radius + "&sensor=false&key=" + API_KEY);
			        //sb.append("&input=" + URLEncoder.encode(input, "utf8"));
			       
					try {
						
						//try to fetch the data
						HttpGet connection = new HttpGet(sb.toString());
						HttpClient client = new DefaultHttpClient();
				        HttpResponse response = client.execute(connection);
				        int status = response.getStatusLine().getStatusCode();
						
						//only carry on if response is OK
						if(status == HttpStatus.SC_OK){
				        	JSONObject mainObject = new JSONObject(convertStreamToString(response.getEntity().getContent()));
				        	places = PlaceController.newPlaceList(mainObject);
				        	for(Place place : places){
				        		finalPlaces.put(place.id, place);
				        	}
				        	
				        }
					}
					catch(Exception e){ 
						e.printStackTrace(); 
					}
				}
			}
			return finalPlaces;
		}

	    private String convertStreamToString(java.io.InputStream is) {
	        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	        return s.hasNext() ? s.next() : "";
	    }

	    @Override
	    protected void onPostExecute(HashMap<String,Place> result) {
	        super.onPostExecute(result);
	        
	        if(result != null){
	        	map.drawPlaces(result);
	        	displayPlaceList(result);
	        }
	    }
	}
}
