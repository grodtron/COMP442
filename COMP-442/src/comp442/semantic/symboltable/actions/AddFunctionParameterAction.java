package comp442.semantic.symboltable.actions;

import comp442.lexical.token.Token;
import comp442.semantic.symboltable.SymbolAction;
import comp442.semantic.symboltable.entries.ParameterEntry;

public class AddFunctionParameterAction extends SymbolAction {
	
	@Override
	public void execute(Token precedingToken) {
		if (context.storedFunction != null) context.storedFunction.addParameter(new ParameterEntry(context.storedId, context.storedType));
	}

}
