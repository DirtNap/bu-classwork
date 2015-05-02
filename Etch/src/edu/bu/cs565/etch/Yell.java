package edu.bu.cs565.etch;

public class Yell extends Statement {
	
	public Yell(Scope scope) {
		super(scope);
	}

	
	@Override
	public void Execute() {
		for (String token : this.tokens) {
			Variable vToken = Variable.Create(null, token);
			String str;
			if (vToken.type == VarType.SYMBOL) {
				str = this.scope.resolve(token).getValue().toString();
			} else if (vToken.type == VarType.STRING)  {
				str = token.replace("\"", "");
			} else {
				str = token;
			}			
			System.err.printf("%s ", str);
		}
		System.err.println();
	}

}
