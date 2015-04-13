package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;


public class PushVariableNameAction extends ExpressionAction {
	
	@Override
	public void execute(Token precedingToken) throws CompilerError {
		context.getCurrent().pushIdentifier(precedingToken.lexeme);		
	}

}
