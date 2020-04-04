package Models;

public class Entity {
	
	private String type;
	private int startIndex;
	private int endIndex;
	public Resolution resolution;
	private double score;
	public String entity;
	
	
	
	
	@Override
	public String toString() {
		return "Entity [type=" + type + ", startIndex=" + startIndex + ", endIndex=" + endIndex + ", resolution="
				+ resolution + ", score=" + score + ", entity=" + entity + "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	public Resolution getResolution() {
		return resolution;
	}
	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}

}
