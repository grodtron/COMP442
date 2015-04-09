package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;
import comp442.semantic.expressions.VariableBuilder;

public class FinishVariableAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {

		try{
			System.out.println("=== calculated offset: " + ((VariableBuilder)(context.getCurrent())).get() + " ===");
			context.popChild();
		}catch(RuntimeException e){
			System.out.println("wooooooopsy " + e);
		}
		
//		if(context.variableBuilder == null){
//			throw new CompilerError("Something has gone very wrong in the syntactic/lexical stage");
//		}else{
//			System.out.println("=== calculated offset: " + context.variableBuilder.get() + " ===");
//			context.variableBuilder = null;
//		}
	}
}
