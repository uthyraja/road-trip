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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsController {
	private GoogleMap map;
	LocationController location;
	
	private int defaultZoom = 15;
	
	public MapsController(MapsPage current) throws Exception{
		this.map = ((MapFragment) current.getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		map.getUiSettings().setCompassEnabled(true);
		map.getUiSettings().setRotateGesturesEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(false);
		map.setMyLocationEnabled(true);
		
		updateToCurrentPossition(current);
	}

	public void updateToCurrentPossition(MapsPage current) throws Exception {
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
		map.addPolyline(trip.GetPolylineOptions());
		addMarker(trip.getStart(), "Origin");
		addMarker(trip.getEnd(), "Destination");
		drawPlacesArray(trip.getPitStops());
		
		animateToTrip(trip);
	}
	
	public void animateToTrip(Trip trip) {
		map.animateCamera(CameraUpdateFactory.newLatLngBounds(trip.getMapBounds(), 100));//new LatLngBounds(sw,ne), 100));
	}

	public void addMarker(Point point, String title) {
		map.addMarker(new MarkerOptions()
			.position(new LatLng(point.getLatitude(), point.getLongitude()))
			.title(title));
	}
	
	public Marker addPlace(Place place) {
		return map.addMarker(new MarkerOptions()
			.position(new LatLng(place.location.getLatitude(), place.location.getLongitude()))
			.title(place.name)
			.snippet(place.vicinity)
			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
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
	public void drawPlacesArray(ArrayList<Place> places){
		for(Place place : places){
			map.addMarker(new MarkerOptions()
			.position(new LatLng(place.location.getLatitude(), place.location.getLongitude()))
			.title(place.name)
			.snippet(place.vicinity)
			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}

	}

	public Marker addPlace_Move(Place place) throws Exception {
		Marker marker = map.addMarker(new MarkerOptions()
		.position(new LatLng(place.location.getLatitude(), place.location.getLongitude()))
		.title(place.name)
		.snippet(place.vicinity)
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		
		animateToPossition(place.location);
		
		return marker;
	}
}
