package nz.ac.waikato.cs.roadtrip.asyncTasks;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import nz.ac.waikato.cs.roadtrip.controllers.PlaceController;
import nz.ac.waikato.cs.roadtrip.models.DetailedPlace;
import android.os.AsyncTask;

public class GetPlaceDetailsAsync extends AsyncTask<String, String, ArrayList<DetailedPlace>>{

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_SEARCH = "/search";
	private static final String OUT_JSON = "/json";
	private static final String API_KEY = "AIzaSyBtffHwqCLotld7p15WG8JcGXkrVzratL4";
	// private static final String RADIUS = "1000";
	
	@Override
	protected ArrayList<DetailedPlace> doInBackground(String... arg0) {

		for (String location : arg0) {
			
			//build url
			StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_SEARCH + OUT_JSON);
	        sb.append("?location=" + location + "&sensor=false&key=" + API_KEY);
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
		        	ArrayList<DetailedPlace> places = PlaceController.newPlaceList(mainObject);
		        	return places;
		        }
			}
			catch(Exception e){ 
				e.printStackTrace(); 
			}
		}
		return null;
	}

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @Override
    protected void onPostExecute(ArrayList<DetailedPlace> result) {
        super.onPostExecute(result);
        
        if(result != null){
        	//map.drawTrip(result);
        }
    }
}


