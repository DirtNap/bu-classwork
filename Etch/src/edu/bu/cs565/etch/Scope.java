package edu.bu.cs565.etch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.bu.cs565.etch.exceptions.VariableNotFoundException;

public class Scope implements Executable{
	private final Scope parent;
	private Map<String, Variable> symbolTable;
	private ArrayList<AbstractStatement> statements;
	private Scope() {
		this(null);
	}
	private Scope(Scope parent) {
		this.parent = parent;
		this.symbolTable = new HashMap<>();
		this.statements = new ArrayList<>();
	}
	public Variable resolve(String name) {
		if (this.symbolTable.containsKey(name)) {
			return this.symbolTable.get(name);
		}
		if (this.parent != null) {
			return this.parent.resolve(name);
		}
		throw new VariableNotFoundException(String.format("Variable %s not found.", name));
	}
	public boolean addToScope(String name, Variable value) {
		if (this.symbolTable.containsKey(name)) {
			return false;
		} else {
			return (this.symbolTable.put(name, value) == null);
		}
	}
	public Scope getChildScope() {
		return new Scope(this);
	}
	public static Scope newGlobalScope() {
		return new Scope();
	}
	public void consume(Scanner s) {
		AbstractStatement stmt;
		while (s.hasNext()) {
			String sToken = s.next();
			stmt = null;
			switch (sToken.toLowerCase()) {
			case "etch":
				stmt = new Etch(this);
				stmt.consume(s);
				break;
			case "tell":
				stmt = new Tell(this);
				stmt.consume(s);
				break;
			case "yell":
				stmt = new Yell(this);
				stmt.consume(s);
				break;
			}
			if (null != stmt) {
				this.statements.add(stmt);
			}
		}
	}
	@Override
	public void Execute() {
		for (Executable s : this.statements) {
			s.Execute();
		}
	}
}
