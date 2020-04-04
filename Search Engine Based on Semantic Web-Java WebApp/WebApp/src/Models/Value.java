package Models;

public class Value {
	
	private String timex;
	private String type;
	public String start;
	private String end;
	
	
	@Override
	public String toString() {
		return "Value [timex=" + timex + ", type=" + type + ", start=" + start + ", end=" + end + "]";
	}
	public String getTimex() {
		return timex;
	}
	public void setTimex(String timex) {
		this.timex = timex;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}

}
