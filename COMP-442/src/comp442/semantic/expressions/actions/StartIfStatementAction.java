package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;
import comp442.semantic.expressions.ExpressionElement;
import comp442.semantic.expressions.IfStatement;

public class StartIfStatementAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {		
		ExpressionElement e = new IfStatement();
		System.out.println("pushing new if statement");
		context.pushChild(e);
	}

}
