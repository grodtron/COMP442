package comp442.semantic.action;

import comp442.lexical.token.Token;

public class CreateFunctionAction implements SemanticAction {

	private final static SemanticContext context = SemanticContext.instance;
	
	@Override
	public void execute(Token precedingToken) {
		context.currentSymbolTable = context.storedFunction.getScope();
		System.out.println("Adding function " + context.storedFunction);
	}

}
