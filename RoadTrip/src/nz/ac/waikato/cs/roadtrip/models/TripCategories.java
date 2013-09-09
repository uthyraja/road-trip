package nz.ac.waikato.cs.roadtrip.models;

import nz.ac.waikato.cs.roadtrip.R;
import android.widget.CheckBox;

public class TripCategories {
	public boolean accommodation;
	public boolean entertainment;
	public boolean food;
	public boolean landMarks;
	public boolean walks;
	
	public TripCategories(boolean accomodation, boolean entertainment, boolean food, boolean landMarks, boolean walks){
		this.accommodation = accomodation;
		this.entertainment = entertainment;
		this.food = food;
		this.landMarks = landMarks;
		this.walks = walks;
	}
}
