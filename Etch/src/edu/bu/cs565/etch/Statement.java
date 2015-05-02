package edu.bu.cs565.etch;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public abstract class Statement extends AbstractStatement {
	protected ArrayList<String> tokens;

	public Statement(Scope scope) {
		super(scope);
		this.tokens = new ArrayList<>();
	}

	@Override
	public void consume(Scanner s) {
		boolean inString = false;
		StringBuilder stringToken = null;
		while (s.hasNext()) {
			String sToken = s.next();
			switch (sToken) {
			case ";":
				return;
			case "\"":
				if (inString) {
					inString = false;
					this.tokens.add(stringToken.toString());
					stringToken = null;
				} else {
					stringToken = new StringBuilder(sToken);
					inString = true;
				}
			default:
				if (inString) {
					stringToken.append(sToken);
				} else {
					this.tokens.add(sToken);
				}
			}
				
		}
	}
	
	@Override
	public String toString() {
		return this.tokens.stream().collect(Collectors.joining(" "));
	}

}
