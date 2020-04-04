package Models;

public class Intent {
	
	private String intent;
	private double score;
	
	
	@Override
	public String toString() {
		return "Intent [intent=" + intent + ", score=" + score + "]";
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

}
