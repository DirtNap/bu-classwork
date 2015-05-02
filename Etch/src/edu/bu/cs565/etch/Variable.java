package edu.bu.cs565.etch;

import edu.bu.cs565.etch.exceptions.InvalidVariableTypeException;

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
			throw new InvalidVariableTypeException();
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
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	public static Variable Create(String name, String value) {
		if (value.toString().matches("^\".*\"$")) {
			return new Variable(name, VarType.STRING, value);
		}
		try {
			Integer.parseInt(value);
			return new Variable(name, VarType.INT, Integer.parseInt(value));
		} catch (NumberFormatException ex) {
			;
		}
		try {
			Double.parseDouble(value);
			return new Variable(name, VarType.FLOAT, Double.parseDouble(value));
		} catch (NumberFormatException ex) {
			;
		}
		return new Variable(name, VarType.SYMBOL, value);
	}
}
