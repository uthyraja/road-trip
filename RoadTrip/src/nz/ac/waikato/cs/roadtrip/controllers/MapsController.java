package nz.ac.waikato.cs.roadtrip.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import nz.ac.waikato.cs.roadtrip.MapsPage;
import nz.ac.waikato.cs.roadtrip.R;
import nz.ac.waikato.cs.roadtrip.models.Place;
import nz.ac.waikato.cs.roadtrip.models.Point;
import nz.ac.waikato.cs.roadtrip.models.Trip;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsController {
	private GoogleMap map;
	LocationController location;
	
	private int defaultZoom = 13;
	
	public MapsController(MapsPage current) throws Exception{
		this.map = ((MapFragment) current.getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		map.getUiSettings().setCompassEnabled(true);
		map.getUiSettings().setRotateGesturesEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(false);
		
		//create a controller for our location
		location = new LocationController(current);
		
		//create a listner for when the location is set
		location.setOnConnectedListner(new Callable<Void>(){

		@Override
		public Void call() throws Exception {
			// TODO Auto-generated method stub
			animateToCurrent();
			return null;
		}});
	}

	public void animateToCurrent() throws Exception{
		animateToPossition(location.GetCurrent());
	}
	
	public void animateToPossition(Point point, int zoom) throws Exception{
		
		LatLng latlng= new LatLng(point.getLatitude(), point.getLongitude());
		
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
	}
	
	public void animateToPossition(LatLng latlng, int zoom) throws Exception{
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
	}
	
	public void animateToPossition(Point point) throws Exception{
		animateToPossition(point, defaultZoom);
	}

	public Point getCurrentPossition() throws Exception {
		// TODO Auto-generated method stub
		return location.GetCurrent();
	}

	public void drawTrip(Trip trip) {
		map.clear();
		map.addPolyline(trip.polylineOptions);
		addMarker(trip.start, "Origin");
		addMarker(trip.end, "Destination");
		
		//zoomToFit();
		LatLng ne= new LatLng(trip.northEast.getLatitude(), trip.northEast.getLongitude());
		LatLng sw= new LatLng(trip.southWest.getLatitude(), trip.southWest.getLongitude());
		map.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(sw,ne), 100));
	}

	public void addMarker(Point point, String title) {
		map.addMarker(new MarkerOptions()
			.position(new LatLng(point.getLatitude(), point.getLongitude()))
			.title(title));
	}
	
	public void drawPlaces(HashMap<String,Place> placeMap){
		for(Place place : placeMap.values()){
			map.addMarker(new MarkerOptions()
			.position(new LatLng(place.location.getLatitude(), place.location.getLongitude()))
			.title(place.name)
			.snippet(place.vicinity)
			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}

	}
}
