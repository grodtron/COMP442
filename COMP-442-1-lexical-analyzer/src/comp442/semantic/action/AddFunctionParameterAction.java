package comp442.semantic.action;

import comp442.lexical.token.Token;
import comp442.semantic.symboltable.entries.ParameterEntry;

public class AddFunctionParameterAction implements SemanticAction {

	private final static SemanticContext context = SemanticContext.instance;
	
	@Override
	public void execute(Token precedingToken) {
		context.storedFunction.addParameter(new ParameterEntry(context.storedId, context.storedType));
		System.out.println("==== Adding parameter ===" + new ParameterEntry(context.storedId, context.storedType));
	}

}
