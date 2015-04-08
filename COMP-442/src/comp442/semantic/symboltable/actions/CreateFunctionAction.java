package comp442.semantic.symboltable.actions;

import comp442.lexical.token.Token;
import comp442.semantic.symboltable.SymbolAction;

public class CreateFunctionAction extends SymbolAction {
	
	@Override
	public void execute(Token precedingToken) {
		if(context.storedFunction != null){
			context.currentSymbolTable.add(context.storedFunction);
			context.currentSymbolTable = context.storedFunction.getScope();
			System.out.println("Adding function " + context.storedFunction);
		}
	}
}
