package edu.bu.cs579.etch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Harness {
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new RuntimeException("No etch file provided.");
		}
		Parser parser = new Parser();
		try {
			Files.lines(Paths.get(args[0])).forEach(l -> parser.addLine(l));
		} catch (IOException e) {
			throw new RuntimeException("Invalid etch file.");
		}
		//Parser parser = new Parser(etchText);
		Scope s = parser.parseTokenStream();
		s.Execute();
	}

}
