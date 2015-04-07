package comp442.semantic.action;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.lexical.token.TokenType;
import comp442.semantic.SymbolTable;
import comp442.semantic.symboltable.entries.ClassEntry;
import comp442.semantic.symboltable.entries.SymbolTableEntry;

public class CreateClassAction extends SemanticAction {

	
	@Override
	public void execute(Token token) throws CompilerError {
		// verify that current scope allows class definition? 
		//  ^ or is this handled by the syntax
		//   ^ Pretty sure this is handled by syntax
		
		// verify that class does not already exist
		
		String name;
		if(token.token == TokenType.tok_id){
			name = token.lexeme;
		}else{
			throw new RuntimeException("precedingToken must be an id for " + getClass().getName());
		}
		
		if( ! context.currentSymbolTable.exists(name)){
			// Create a new symbol table for the new Class
			SymbolTable table = new SymbolTable(context.currentSymbolTable);
			// Create a new Symbol table entry for the new class
			SymbolTableEntry entry = new ClassEntry(name, table);
			// Add the new entry to the current symbol table
			context.currentSymbolTable.add(entry);
			// Set the new table as the current table
			context.currentSymbolTable = table;
			
			System.out.println("Created new scope for class " + name);
		}else{
			context.skipNextCloseScope = true;
			throw new CompilerError("Duplicate class declaration: " + name);
		}
	}

}
