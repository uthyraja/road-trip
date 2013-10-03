package nz.ac.waikato.cs.roadtrip.adapters;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.Marker;

import nz.ac.waikato.cs.roadtrip.MapsPage;
import nz.ac.waikato.cs.roadtrip.R;
import nz.ac.waikato.cs.roadtrip.MapsPage.HttpRequestAsync;
import nz.ac.waikato.cs.roadtrip.adapters.PlaceTypeAdapter.ViewHolder;
import nz.ac.waikato.cs.roadtrip.controllers.MapsController;
import nz.ac.waikato.cs.roadtrip.models.Place;
import nz.ac.waikato.cs.roadtrip.models.Trip;
import nz.ac.waikato.cs.roadtrip.models.TripCategories;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlaceAdapter extends ArrayAdapter<Place> {

	private final ArrayList<Place> listAll;
	private final ArrayList<Place> listSelected;
	private final ArrayList<Boolean> checkBoxes;
	private final Activity context;
	private Marker tempMarker;
	private MapsPage mPage;

	public PlaceAdapter(Activity context, ArrayList<Place> list) {
		super(context, R.layout.place_listview_item, list);
		this.context = context;
		this.listAll = list;
		this.listSelected = new ArrayList<Place>();
		this.tempMarker = null;
		this.mPage = (MapsPage)context;
		this.checkBoxes = new ArrayList<Boolean>();
		
		for(Place p : list)
			checkBoxes.add(false);
	}

	static class ViewHolder {
		protected RelativeLayout rLayout;
		protected TextView title;
		protected TextView distance;
		protected CheckBox checkbox;
		protected Place place;
	}

	@Override
	public View getView(final int position, View convertView_, ViewGroup parent) {
		View view = null;
		//if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.place_listview_item, null);
			final ViewHolder viewHolder = new ViewHolder();

			viewHolder.title = (TextView) view.findViewById(R.id.textViewTitle);
			viewHolder.distance = (TextView) view
					.findViewById(R.id.textViewDistance);
			viewHolder.checkbox = (CheckBox) view
					.findViewById(R.id.checkBoxPlace);
			viewHolder.place = listAll.get(position);
			viewHolder.rLayout = (RelativeLayout)view.findViewById(R.id.relativeLayoutListViewItem);
			
			viewHolder.rLayout.setOnLongClickListener(null);
			viewHolder.rLayout.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					try{
					    tempMarker = null;
						mPage.mDrawerLayout.closeDrawer(Gravity.END);
					    
						Place element = viewHolder.place;
						tempMarker = mPage.map.addPlace_Move(element);
					}
					catch(Exception e){
						
					}
					return false;
				}
			});
			viewHolder.rLayout.setOnTouchListener(null);
			viewHolder.rLayout.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_UP){
						if(tempMarker != null){
							mPage.mDrawerLayout.openDrawer(Gravity.END);
							tempMarker.remove();
							mPage.map.animateToTrip(mPage.trip);
							tempMarker = null;
						}
						
					}
					return false;
				}
			});
			
			viewHolder.checkbox.setOnCheckedChangeListener(null);
			viewHolder.checkbox.setChecked(checkBoxes.get(position));
			viewHolder.checkbox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Place element = viewHolder.place;
							checkBoxes.add(position, isChecked);
							checkBoxes.remove(position + 1);
							// if check box is checked, and not already in list,
							// add it
							if (isChecked)
								mPage.trip.addPitstop(element);
							// else if not checked, and already in the list,
							// remove it
							else
								mPage.trip.removePitstop(element);

							mPage.getTripFromGoogleApi();
						}
					});

			view.setTag(viewHolder);

		Place place = listAll.get(position);

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.title.setText(place.name);
		holder.distance.setText(place.getFormattedDistance());
		holder.place = place;

		return view;
	}

	public ArrayList<Place> getPlaceTypes() {
		// TODO Auto-generated method stub
		return listSelected;
	}
}
