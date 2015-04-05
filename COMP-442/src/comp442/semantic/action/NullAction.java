package comp442.semantic.action;

import comp442.lexical.token.Token;

public class NullAction implements SemanticAction {

	private NullAction() {}
	
	public final static NullAction instance = new NullAction();
	
	@Override
	public void execute(Token precedingToken) {
		throw new RuntimeException(getClass().getName() + "Should never actually end up being called");
	}

}
