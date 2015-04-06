package comp442.semantic.action;

import comp442.lexical.token.Token;

public class PushVariableNameAction extends SemanticAction {

	@Override
	public void execute(Token precedingToken) {
		System.out.println("Pushing name " + precedingToken.lexeme);
	}

}
