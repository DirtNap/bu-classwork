package edu.bu.cs579.etch;

import java.util.HashMap;
import java.util.Map;

import edu.bu.cs579.etch.exceptions.VariableNotFoundException;

public class Scope {
	private final Scope parent;
	private Map<String, Variable> symbolTable;
	private Scope() {
		this(null);
	}
	private Scope(Scope parent) {
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
}
