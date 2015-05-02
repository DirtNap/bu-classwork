package edu.bu.cs579.etch;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Parser {
	private boolean inLine;
	private String currentLine;
	private ArrayList<String> lines;

	public Parser() {
		this.inLine = false;
		this.lines = new ArrayList<>();
	}
	
	public void addLine(String line) {
		line = line.replaceAll("#.*$", "") // Remove comments
				   .replaceAll("([\",;\\{\\}\\\\\\[\\]=\\-\\+\\*/])", " $1 ")
				   .trim() // Remove leading and trailing whitespace
				   .replaceAll("\\s+", " "); // Collapse spaces
		if (line.length() == 0) {
			return;
		}
		char finalChar = line.charAt(line.length() - 1);
		if (finalChar == '\\') {
			this.inLine = true;
			this.currentLine = String.format("%s%s", 
					this.currentLine, line.substring(0, line.length() - 2).trim());
		} else {
			if (this.inLine) {
				this.currentLine = String.format("%s%s", this.currentLine, line);
			} else {
				this.currentLine = line;
			}
			this.inLine = false;
		}
		if (!this.inLine) {
			this.lines.add(this.currentLine);
			this.currentLine = "";
		}
	}

	public Scope parseTokenStream() {
		Scope scope = Scope.newGlobalScope();
		Scanner lineScanner = new Scanner(this.lines.stream().collect(Collectors.joining(" ")));
		scope.consume(lineScanner);
		lineScanner.close();
		return scope;
	}

	public static boolean isEtchWord(String sToken) {
		String word = sToken.toLowerCase();
		return (word.equalsIgnoreCase("etch") || word.equalsIgnoreCase("tell") || word.equalsIgnoreCase("yell"));
	}
}
