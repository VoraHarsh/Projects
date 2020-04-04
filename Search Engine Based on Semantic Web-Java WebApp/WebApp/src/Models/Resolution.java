package Models;

import java.util.ArrayList;

public class Resolution {
	
	private String comment;
	private String time;
	public String value;
	private ArrayList<Value> values;
	public String subtype; 
	
	
	
	
	
	
	@Override
	public String toString() {
		return "Resolution [comment=" + comment + ", time=" + time + ", value=" + value + ", values=" + values
				+ ", subtype=" + subtype + "]";
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public ArrayList<Value> getValues() {
		return values;
	}
	public void setValues(ArrayList<Value> values) {
		this.values = values;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
