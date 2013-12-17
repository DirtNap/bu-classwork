package edu.bu.cs232;

import java.io.Serializable;

public class ScoreEntry implements Comparable<ScoreEntry>, Serializable {
	private static final long serialVersionUID = -8919372727186627820L;
	private String playerName;
	private int score;
	
	public ScoreEntry() {
		
	}
	public ScoreEntry(String playerName, int score) {
		this.setPlayerName(playerName);
		this.setScore(score);
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(ScoreEntry e) {
		int result = -1;
		if (null != e) {
			result = Integer.compare(e.getScore(), this.getScore());
			if (result == 0) {
				result = this.getPlayerName().compareToIgnoreCase(e.getPlayerName());
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		return String.format("%04d\t%s", this.getScore(), this.getPlayerName());
	}
	
	@Override
	public boolean equals(Object o) {
		if (null == o) {
			return false;
		}
		if (this == o) {
			return true;
		}
		try {
			ScoreEntry e = (ScoreEntry) o;
			return (this.getScore() == e.getScore());
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Integer.valueOf(this.getScore()).hashCode();
	}
}
