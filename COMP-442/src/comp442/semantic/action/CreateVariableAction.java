package comp442.semantic.action;

import comp442.lexical.token.Token;
import comp442.logging.Log;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.VariableEntry;

public class CreateVariableAction extends SemanticAction {
	
	@Override
	public void execute(Token token) {
		if(context.currentSymbolTable.exists(context.storedId)){
			Log.error.println("Duplicate function declaration: " + context.storedId + " on line " + token.lineno);
		}else{
			SymbolTableEntry entry = new VariableEntry(context.storedId, context.storedType);
			context.currentSymbolTable.add(entry);
			System.out.println(" Added entry: " + entry);			
		}
	}

}
