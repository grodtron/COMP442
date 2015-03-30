package comp442.semantic.action;

import comp442.lexical.token.Token;
import comp442.semantic.symboltable.entries.types.PlainType;

public class StoreTypeAction implements SemanticAction {

	private SemanticContext context = SemanticContext.instance;
	
	@Override
	public void execute(Token precedingToken) {
		context.storedType = new PlainType(precedingToken.lexeme);
	}

}
