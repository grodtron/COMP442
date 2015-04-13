package comp442.semantic.symboltable.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.symboltable.SymbolAction;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.FunctionEntry;

public class StartFunctionAction extends SymbolAction {
	
	@Override
	public void execute(Token token) throws CompilerError {
		if(context.currentSymbolTable.exists(context.storedId)){
			context.storedFunction = null;
			context.skipNextCloseScope = true;
			
			throw new CompilerError("Duplicate function declaration: " + context.storedId);
		}else{
			SymbolTable table = new SymbolTable(context.currentSymbolTable);
			
			context.storedFunction         = new FunctionEntry(context.storedId, context.storedType, table);
			
		}
	}

}
