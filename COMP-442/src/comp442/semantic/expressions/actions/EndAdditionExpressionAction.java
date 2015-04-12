package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.AdditionExpressionFragment;
import comp442.semantic.expressions.ExpressionAction;
import comp442.semantic.expressions.ExpressionElement;

public class EndAdditionExpressionAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		ExpressionElement top = context.getCurrent();
		if(top instanceof AdditionExpressionFragment){
			context.finishTopElement();
			System.err.println("END ADD EXPR ELEMENT");
		}else{
			throw new InternalCompilerError("Expected " + AdditionExpressionFragment.class.getName() + " but was " + top.getClass().getName());
		}
	}

}
