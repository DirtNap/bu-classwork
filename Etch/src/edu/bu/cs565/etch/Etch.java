package edu.bu.cs565.etch;

public class Etch extends Statement {
	
	public Etch(Scope scope) {
		super(scope);
	}

	@Override
	public void Execute() {
		int ct = this.tokens.size();
		if (ct < 3) {
			throw new RuntimeException();
		}
		String varName = this.tokens.get(--ct);
		if (!this.tokens.get(--ct).equals("on")) {
			throw new RuntimeException();
		}
		if (ct == 1) {
			this.scope.addToScope(varName, Variable.Create(varName, this.tokens.get(--ct)));
		} else {
			
		}
	}

}
