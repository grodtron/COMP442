package comp442.semantic.expressions.actions;

import comp442.codegen.MathOperation;
import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;

public class PushMultiplicationOperationAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		context.getCurrent().pushMultiplicationOperator(
				MathOperation.fromToken(precedingToken.lexeme));
	}

}
