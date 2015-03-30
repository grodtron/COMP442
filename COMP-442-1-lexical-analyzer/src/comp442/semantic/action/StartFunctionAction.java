package comp442.semantic.action;

import comp442.lexical.token.Token;
import comp442.semantic.SymbolTable;
import comp442.semantic.symboltable.entries.FunctionEntry;

public class StartFunctionAction implements SemanticAction {

	private final static SemanticContext context = SemanticContext.instance;
	
	@Override
	public void execute(Token precedingToken) {
		context.storedFunction = new FunctionEntry(context.storedId, context.storedType, new SymbolTable(context.currentSymbolTable));
	}

}
