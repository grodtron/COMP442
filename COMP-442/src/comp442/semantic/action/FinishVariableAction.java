package comp442.semantic.action;

import comp442.lexical.token.Token;

public class FinishVariableAction extends SemanticAction {

	@Override
	public void execute(Token precedingToken) {
		System.out.println("variable done!!");
	}

}
