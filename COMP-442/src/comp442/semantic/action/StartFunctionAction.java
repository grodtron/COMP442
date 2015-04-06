package comp442.semantic.action;

import comp442.lexical.token.Token;
import comp442.logging.Log;
import comp442.semantic.SymbolTable;
import comp442.semantic.symboltable.entries.FunctionEntry;
import comp442.semantic.symboltable.entries.ParameterEntry;
import comp442.semantic.symboltable.entries.types.PrimitiveType;

public class StartFunctionAction extends SemanticAction {
	
	@Override
	public void execute(Token token) {
		if(context.currentSymbolTable.exists(context.storedId)){
			Log.error.println("Duplicate function declaration: " + context.storedId + " on line " + token.lineno);
			context.storedFunction = null;
			context.skipNextCloseScope = true;
		}else{
			SymbolTable table = new SymbolTable(context.currentSymbolTable);
			
			context.storedFunction         = new FunctionEntry(context.storedId, context.storedType, table);
			
			// Adding these pseudo-parameters ensures that all stack-frame offsets will make sense.
			//
			// '@' sign guarantees no name collisions
			ParameterEntry returnPcAddr    = new ParameterEntry("@returnPcAddr",    new PrimitiveType("int"));
			ParameterEntry returnValueAddr = new ParameterEntry("@returnValueAddr", new PrimitiveType("int"));

			table.add(returnValueAddr);
			table.add(returnPcAddr);
			
		}
	}

}
