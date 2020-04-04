package Models;

import java.util.ArrayList;

public class LuisJsonModel {
	
	private String query;
	private TopScoringIntent topScoringIntent;
	private ArrayList<Intent> intents;
	private ArrayList<Entity> entities;
	private SentimentAnalysis sentimentAnalysis;
	
	
	
	@Override
	public String toString() {
		return "LuisJsonModel [query=" + query + ", topScoringIntent=" + topScoringIntent + ", intents=" + intents
				+ ", entities=" + entities + ", sentimentAnalysis=" + sentimentAnalysis + "]";
	}
	public LuisJsonModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LuisJsonModel(String query, TopScoringIntent topScoringIntent, ArrayList<Intent> intents,
			ArrayList<Entity> entities, SentimentAnalysis sentimentAnalysis) {
		super();
		this.query = query;
		this.topScoringIntent = topScoringIntent;
		this.intents = intents;
		this.entities = entities;
		this.sentimentAnalysis = sentimentAnalysis;
	}
	public SentimentAnalysis getSentimentAnalysis() {
		return sentimentAnalysis;
	}
	public void setSentimentAnalysis(SentimentAnalysis sentimentAnalysis) {
		this.sentimentAnalysis = sentimentAnalysis;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public TopScoringIntent getTopScoringIntent() {
		return topScoringIntent;
	}
	public void setTopScoringIntent(TopScoringIntent topScoringIntent) {
		this.topScoringIntent = topScoringIntent;
	}
	public ArrayList<Intent> getIntents() {
		return intents;
	}
	public void setIntents(ArrayList<Intent> intents) {
		this.intents = intents;
	}
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

}
