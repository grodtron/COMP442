package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;
import comp442.semantic.expressions.ExpressionElement;
import comp442.semantic.expressions.RelationExpressionFragment;

public class EndRelationExpressionAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		ExpressionElement top = context.getCurrent();
		if(top instanceof RelationExpressionFragment){
			context.finishTopElement();
		}else{
			// TODO - this throws a lot ...
			// after the 'condition' part of the for loop
			// this is not really an error .... throw new InternalCompilerError("Expected " + RelationExpressionFragment.class.getName() + " but was " + top.getClass().getName());
		}
	}

}
