package nz.ac.waikato.cs.roadtrip.asyncTasks;

import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;

import nz.ac.waikato.cs.roadtrip.MapsPage;
import nz.ac.waikato.cs.roadtrip.controllers.TripController;
import nz.ac.waikato.cs.roadtrip.models.Trip;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import android.os.AsyncTask;

public class HttpRequestAsync extends AsyncTask<String, String, Trip>{

    @Override
    protected Trip doInBackground(String... uri) {
    	try{
			String baseURI = "http://maps.googleapis.com/maps/api/directions/xml?";
			String parameters = String.format("origin=%s&destination=%s&sensor=true", uri[0], uri[1]);
			String fullURI = baseURI + parameters;
			
			HttpGet connection = new HttpGet(fullURI.replace(" ", "%20"));
			HttpClient client = new DefaultHttpClient();
	        HttpResponse response = client.execute(connection);
	        int res = response.getStatusLine().getStatusCode();
	        
	        if(res == HttpStatus.SC_OK){
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
    
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @Override
    protected void onPostExecute(Trip result) {
        super.onPostExecute(result);
        
        if(result != null){
        	
        }
    }
}
