package nz.ac.waikato.cs.roadtrip.adapters;

public class PlaceType {

	public PlaceType(String string) {
		this.title = string;
		this.isChecked = false;
	}
	public String title;
	public boolean isChecked;
	
	public void setSelected(boolean checked) {
		isChecked = checked;
		
	}
	public String getName() {
		return title;
	}
	public boolean isSelected() {
		return isChecked;
	}

}
