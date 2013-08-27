package nz.ac.waikato.cs.roadtrip.controllers;

import java.util.concurrent.Callable;

import nz.ac.waikato.cs.roadtrip.models.Point;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

public class LocationController implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {

	private LocationClient location;
	private Boolean connected = false;
	private Callable<Void> onConnectListner;
	
	
	public LocationController(Context current) throws Exception{
		location = new LocationClient(current, this,this);
		location.connect();
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		connected = false;
	}

	@Override
	public void onConnected(Bundle arg0) {
		connected = true;
		try {
			onConnectListner.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDisconnected() {
		connected = false;
	}

	public Point GetCurrent() throws Exception{
		Location current = location.getLastLocation();
		return new Point(current.getLatitude(), current.getLongitude());
	}

	public Boolean isConnected(){
		return connected;
	}

	public void setOnConnectedListner(Callable<Void> onConnected) {
		onConnectListner = onConnected;
	}
}
