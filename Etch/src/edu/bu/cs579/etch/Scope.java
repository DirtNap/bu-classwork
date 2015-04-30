package edu.bu.cs579.etch;

import java.util.HashMap;
import java.util.Map;

import edu.bu.cs579.etch.exceptions.VariableNotFoundException;

public class Scope {
	private final Scope parent;
	private Map<String, Variable> symbolTable;
	public Scope() {
		this(null);
	}
	public Scope(Scope parent) {
		this.parent = parent;
		this.symbolTable = new HashMap<>();
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
	public boolean addToScope(String name, Object value) {
		return false;
	}
}
