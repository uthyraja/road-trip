package nz.ac.waikato.cs.roadtrip.controllers;

import java.util.concurrent.Callable;

import nz.ac.waikato.cs.roadtrip.MapsPage;
import nz.ac.waikato.cs.roadtrip.R;
import nz.ac.waikato.cs.roadtrip.models.Point;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

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
	
	public void animateToPossition(Point point) throws Exception{
		animateToPossition(point, defaultZoom);
	}
}
