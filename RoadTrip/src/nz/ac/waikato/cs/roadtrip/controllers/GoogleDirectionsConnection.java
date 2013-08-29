package nz.ac.waikato.cs.roadtrip.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GoogleDirectionsConnection {
	public static void getDirections(String origin, String destination) throws ClientProtocolException, IOException, SAXException, ParserConfigurationException{
		String baseURI = "http://maps.googleapis.com/maps/api/directions/xml?";
		String parameters = String.format("origin=\"%s\"&destination=\"%s\"&sensor=%s", origin, destination, true);
		String fullURI = baseURI + parameters;
		
		HttpGet connection = new HttpGet(fullURI);
		HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(connection);
        int res = response.getStatusLine().getStatusCode();
        
        if(res == HttpStatus.SC_OK){
        	InputSource is = new InputSource(new InputStreamReader(response.getEntity().getContent()));
        	Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        	
        	
        }
	}
	
}
