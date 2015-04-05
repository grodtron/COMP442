package comp442.semantic.action;

import comp442.lexical.token.Token;
import comp442.semantic.symboltable.entries.ParameterEntry;

public class AddFunctionParameterAction implements SemanticAction {

	private final static SemanticContext context = SemanticContext.instance;
	
	@Override
	public void execute(Token precedingToken) {
		if (context.storedFunction != null) context.storedFunction.addParameter(new ParameterEntry(context.storedId, context.storedType));
	}

}
