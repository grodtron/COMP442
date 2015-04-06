package comp442.semantic.action;

import comp442.lexical.token.Token;

public class PushIndexAction extends SemanticAction {

	@Override
	public void execute(Token precedingToken) {
		System.out.println("  pushing index " + precedingToken.lexeme);
	}

}
