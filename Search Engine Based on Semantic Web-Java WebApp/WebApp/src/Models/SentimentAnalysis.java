package Models;

public class SentimentAnalysis {
	
	private String label;
	private String score;
	
	
	@Override
	public String toString() {
		return "SentimentAnalysis [label=" + label + ", score=" + score + "]";
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}

}
