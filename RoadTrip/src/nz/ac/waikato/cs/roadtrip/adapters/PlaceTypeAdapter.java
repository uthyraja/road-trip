package nz.ac.waikato.cs.roadtrip.adapters;

import java.util.ArrayList;
import java.util.List;

import nz.ac.waikato.cs.roadtrip.R;
import nz.ac.waikato.cs.roadtrip.models.TripCategories;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class PlaceTypeAdapter extends ArrayAdapter<PlaceType>{

	private final List<PlaceType> list;
	  private final Activity context;
	  private TripCategories tripCat;

	  public PlaceTypeAdapter(Activity context, List<PlaceType> list) {
	    super(context, R.layout.place_types_listview_item, list);
	    this.context = context;
	    this.list = list;
	    tripCat = new TripCategories(false);
	  }

	  static class ViewHolder {
	    protected TextView text;
	    protected CheckBox checkbox;
	  }

	  public String getParamsUri(){
		  return tripCat.getParamURI();
	  }
	  
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    View view = null;
	    if (convertView == null) {
	      LayoutInflater inflator = context.getLayoutInflater();
	      view = inflator.inflate(R.layout.place_types_listview_item, null);
	      final ViewHolder viewHolder = new ViewHolder();
	      viewHolder.text = (TextView) view.findViewById(R.id.txtTitle);
	      viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkBox);
	      viewHolder.checkbox
	          .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

	            @Override
	            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	            	PlaceType element = (PlaceType) viewHolder.checkbox.getTag();
	            	element.setSelected(buttonView.isChecked());
	            	if(isChecked)
	            		tripCat.addCategory(element.title);
	            	else
	            		tripCat.removeCategory(element.title);
	            }
	          });
	      view.setTag(viewHolder);
	      viewHolder.checkbox.setTag(list.get(position));
	    } else {
	      view = convertView;
	      ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
	    }
	    ViewHolder holder = (ViewHolder) view.getTag();
	    holder.text.setText(list.get(position).getName());
	    holder.checkbox.setChecked(list.get(position).isSelected());
	    return view;
	  }

	public TripCategories getPlaceTypes() {
		// TODO Auto-generated method stub
		return tripCat;
	}

	
}


