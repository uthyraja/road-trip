package nz.ac.waikato.cs.roadtrip.models;

import java.io.Serializable;
import java.util.ArrayList;

import nz.ac.waikato.cs.roadtrip.R;
import nz.ac.waikato.cs.roadtrip.TripPage;
import android.widget.CheckBox;

public class TripCategories implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean allCategories;
	private ArrayList<String> types;
	
	public TripCategories(boolean all){
		this.allCategories = all;
		types = new ArrayList<String>();
	}
	
	public String getParamURI(){
		if(allCategories)
			return "";
			
		StringBuilder sb = new StringBuilder();
		sb.append("&types=");
			
		for(int i = 0; i < types.size(); i++){
			sb.append(types.get(i));
			if(i != types.size() - 1)
				sb.append("%7c");
		}
			
		return sb.toString();
	}
	
	public void addCategory(String cat){
		if(!types.contains(cat))
			types.add(cat);
	}

	public void removeCategory(String title) {
		if(types.contains(title))
			types.remove(title);
	}
	
	
}
