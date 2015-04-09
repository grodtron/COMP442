package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.AssignmentExpression;
import comp442.semantic.expressions.ExpressionAction;
import comp442.semantic.expressions.ExpressionElement;

public class StartAssignmentStatementAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		System.err.println("===== StartAssignmentStatement previousToken was: " + precedingToken.lexeme + " on line "+ precedingToken.lineno);
		
		ExpressionElement e = new AssignmentExpression();
		context.pushChild(e);
	}

}
