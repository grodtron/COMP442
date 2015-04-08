package comp442.semantic.symboltable.actions;

import comp442.lexical.token.Token;
import comp442.semantic.symboltable.SymbolAction;

public class StoreIdAction extends SymbolAction {
	
	@Override
	public void execute(Token precedingToken) {
		context.storedId = precedingToken.lexeme;
	}

}
