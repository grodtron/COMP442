package comp442.semantic.action;

import comp442.lexical.token.Token;

public class StoreIdAction implements SemanticAction {

	private SemanticContext context = SemanticContext.instance;
	
	@Override
	public void execute(Token precedingToken) {
		context.storedId = precedingToken.lexeme;
		System.out.println(" stored ID " + context.storedId);
	}

}
