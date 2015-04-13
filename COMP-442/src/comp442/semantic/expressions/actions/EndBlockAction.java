package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;
import comp442.semantic.expressions.ExpressionElement;
import comp442.semantic.expressions.StatementBlock;

public class EndBlockAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		ExpressionElement top = context.getCurrent();
		if(top instanceof StatementBlock){
			System.out.println("popping block from top");
			context.finishTopElement();
		}else{
			throw new InternalCompilerError("Expected " + StatementBlock.class.getName() + " but was " + top.getClass().getName());
		}
	}

}
