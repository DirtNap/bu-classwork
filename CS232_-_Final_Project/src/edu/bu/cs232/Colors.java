package edu.bu.cs232;

import java.util.Random;

public enum Colors {
	WHITE     ("W", "White"),
	BLACK     ("B", "Black"),
	GREEN     ("G", "Green"),
	RED       ("R", "Red"),
	YELLOW    ("Y", "Yellow"),
	PINK      ("P", "Pink");

	private String selector;
	private String descriptor;
	private static Random randomGenerator;
	private Colors(String selector, String descriptor) {
		this.selector = selector;
		this.descriptor = descriptor;
	}
	public String getSelector() {
		return this.selector;
	}
	public String getDescriptor() {
		return this.descriptor;
	}
    private static Random getGenerator() {
        if (Colors.randomGenerator == null) {
        	Colors.randomGenerator = new Random();
        }
        return Colors.randomGenerator;
    }
    public static Colors[] randomPattern(int length, Colors ... colors) {
    	Colors[] result = new Colors[length];
    	for (int i = 0; i < length; ++i) {
    		result[i] = colors[Colors.getGenerator().nextInt(colors.length)];
    	}
    	return result;
    }
}
