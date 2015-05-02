package edu.bu.cs565.etch;

import java.util.Scanner;

public abstract class AbstractStatement implements Executable {
	protected Scope scope;

	public AbstractStatement(Scope scope) {
		this.scope = scope;
	}

	public abstract void consume(Scanner s);
}
