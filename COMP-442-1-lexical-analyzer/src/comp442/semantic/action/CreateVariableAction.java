package comp442.semantic.action;

import comp442.lexical.token.Token;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.VariableEntry;

public class CreateVariableAction implements SemanticAction {

	private SemanticContext context = SemanticContext.instance;
	
	@Override
	public void execute(Token precedingToken) {
		SymbolTableEntry entry = new VariableEntry(context.storedId, context.storedType);
		context.currentSymbolTable.add(entry);
		System.out.println(" Added entry: " + entry);
	}

}
