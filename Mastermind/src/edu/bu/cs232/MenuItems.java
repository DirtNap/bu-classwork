package edu.bu.cs232;

public enum MenuItems {
	Scores ("S", "See High Scores"),
	Play   ("P", "Play a Game"),
	Exit   ("X", "Exit the Game."),
	Easy   ("E", "Easy"),
	Medium ("M", "Medium"),
	Hard   ("H", "Hard"),
	Rules  ("I", "Read the instructions");
	
	private final String selector;
	private final String descriptor;
	MenuItems(String selector, String description) {
		this.selector = selector.toUpperCase();
		this.descriptor = description;
	}
	public String getSelector() {
		return this.selector;
	}
	public String getDescriptor() {
		return this.descriptor;
	}
}
