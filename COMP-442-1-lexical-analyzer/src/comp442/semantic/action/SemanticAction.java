package comp442.semantic.action;

import comp442.lexical.token.Token;

public interface SemanticAction {

	public void execute(Token precedingToken);
	
}
