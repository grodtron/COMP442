package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;
import comp442.semantic.expressions.ExpressionElement;
import comp442.semantic.expressions.FunctionCallExpressionFragment;

public class StartFunctionCallAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		System.err.println("Push function call to function: " + precedingToken.lexeme);
		ExpressionElement e = new FunctionCallExpressionFragment(precedingToken.lexeme);
		context.pushChild(e);
	}

}
