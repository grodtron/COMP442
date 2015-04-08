package comp442.semantic.symboltable.actions;

import comp442.lexical.token.Token;
import comp442.semantic.symboltable.SymbolAction;

public class NullAction extends SymbolAction {

	private NullAction() {}
	
	public final static NullAction instance = new NullAction();
	
	@Override
	public void execute(Token precedingToken) {
		throw new RuntimeException(getClass().getName() + "Should never actually end up being called");
	}

}
