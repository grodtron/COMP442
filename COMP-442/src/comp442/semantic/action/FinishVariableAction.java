package comp442.semantic.action;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;

public class FinishVariableAction extends SemanticAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		if(context.variableBuilder == null){
			throw new CompilerError("Something has gone very wrong in the syntactic/lexical stage");
		}else{
			System.out.println("=== calculated offset: " + context.variableBuilder.get() + " ===");
			context.variableBuilder = null;
		}
	}

}
