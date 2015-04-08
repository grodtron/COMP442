package comp442.semantic.symboltable.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.symboltable.SymbolAction;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.VariableEntry;

public class CreateVariableAction extends SymbolAction {
	
	@Override
	public void execute(Token token) throws CompilerError {
		if(context.currentSymbolTable.exists(context.storedId)){
			throw new CompilerError("Duplicate function declaration: " + context.storedId);
		}else{
			SymbolTableEntry entry = new VariableEntry(context.storedId, context.storedType);
			context.currentSymbolTable.add(entry);
			System.out.println(" Added entry: " + entry);			
		}
	}

}
