package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;
import comp442.semantic.expressions.ExpressionElement;
import comp442.semantic.expressions.MultiplicationExpressionFragment;

public class EndMultiplicationExpressionAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		ExpressionElement top = context.getCurrent();
		if(top instanceof MultiplicationExpressionFragment){
			context.finishTopElement();
		}else{
			//throw new InternalCompilerError("Expected " + MultiplicationExpressionFragment.class.getName() + " but was " + top.getClass().getName());
		}
	}

}
