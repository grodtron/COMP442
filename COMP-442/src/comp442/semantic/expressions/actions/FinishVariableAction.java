package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.symboltable.SymbolAction;

public class FinishVariableAction extends SymbolAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
//		if(context.variableBuilder == null){
//			throw new CompilerError("Something has gone very wrong in the syntactic/lexical stage");
//		}else{
//			System.out.println("=== calculated offset: " + context.variableBuilder.get() + " ===");
//			context.variableBuilder = null;
//		}
	}
}
