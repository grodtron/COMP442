package comp442.semantic.action;

import comp442.lexical.token.Token;
import comp442.logging.Log;
import comp442.semantic.SymbolTable;
import comp442.semantic.symboltable.entries.FunctionEntry;

public class StartFunctionAction implements SemanticAction {

	private final static SemanticContext context = SemanticContext.instance;
	
	@Override
	public void execute(Token token) {
		if(context.currentSymbolTable.exists(context.storedId)){
			Log.error.println("Duplicate function declaration: " + context.storedId + " on line " + token.lineno);
			context.storedFunction = null;
			context.skipNextCloseScope = true;
		}else{
			context.storedFunction = new FunctionEntry(context.storedId, context.storedType, new SymbolTable(context.currentSymbolTable));
		}
	}

}
