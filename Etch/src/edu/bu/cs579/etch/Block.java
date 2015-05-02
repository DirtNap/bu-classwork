package edu.bu.cs579.etch;

import java.util.Scanner;

public abstract class Block extends AbstractStatement {

	private Scope inner;

	public Block(Scope scope) {
		super(scope.getChildScope());
	}

}
