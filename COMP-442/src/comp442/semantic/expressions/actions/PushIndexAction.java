package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;

public class PushIndexAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		System.out.println(" push index! " + precedingToken.lexeme);
		context.getCurrent().pushIndex(precedingToken.lexeme);
	}
}
