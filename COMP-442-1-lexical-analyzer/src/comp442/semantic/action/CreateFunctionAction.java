package comp442.semantic.action;

import comp442.lexical.token.Token;

public class CreateFunctionAction implements SemanticAction {

	private final static SemanticContext context = SemanticContext.instance;
	
	@Override
	public void execute(Token precedingToken) {
		if(context.storedFunction != null){
			context.currentSymbolTable.add(context.storedFunction);
			context.currentSymbolTable = context.storedFunction.getScope();
			System.out.println("Adding function " + context.storedFunction);
		}
	}
}
