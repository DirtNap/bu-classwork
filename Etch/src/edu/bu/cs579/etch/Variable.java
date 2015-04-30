package edu.bu.cs579.etch;

import edu.bu.cs579.etch.exceptions.InvalidVariableType;

public class Variable {
	public final VarType type;
	public final String name;
	private Object value;
	
	public Variable(String name, VarType type) {
		this(name, type, null);
	}
	public Variable(String name, VarType type, Object value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	public Object getValue() {
		return this.value;
	}
	public void setValue(Object value) {
		if (this.checkType(value)) {
			this.value = value;
		} else {
			throw new InvalidVariableType();
		}
	}
	private boolean checkType(Object value) {
		try {
			switch (this.type) {
			case INT:
				Integer.parseInt(value.toString());
			case FLOAT:
				Double.parseDouble(value.toString());
			case STRING:
				value.toString();
			}
		} catch (ClassCastException ex) {
			return false;
		}
		return true;
	}
}
