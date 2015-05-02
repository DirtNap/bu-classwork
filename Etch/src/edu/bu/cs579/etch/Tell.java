package edu.bu.cs579.etch;

public class Tell extends Statement {
	
	public Tell(Scope scope) {
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
			System.out.printf("%s ", str);
		}
		System.out.println();
	}

}
