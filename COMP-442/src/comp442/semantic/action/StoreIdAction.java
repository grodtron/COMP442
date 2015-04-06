package comp442.semantic.action;

import comp442.lexical.token.Token;

public class StoreIdAction extends SemanticAction {
	
	@Override
	public void execute(Token precedingToken) {
		context.storedId = precedingToken.lexeme;
	}

}
