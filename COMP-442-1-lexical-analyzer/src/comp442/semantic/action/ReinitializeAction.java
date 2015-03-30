package comp442.semantic.action;

import comp442.lexical.token.Token;

public class ReinitializeAction implements SemanticAction {

	@Override
	public void execute(Token precedingToken) {
		SemanticContext.instance.init();
	}

}
