package comp442.semantic.action;

import comp442.lexical.token.Token;
import comp442.logging.Log;
import comp442.semantic.symboltable.entries.ClassEntry;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.types.ClassType;
import comp442.semantic.symboltable.entries.types.PrimitiveType;

public class StoreTypeAction implements SemanticAction {

	private SemanticContext context = SemanticContext.instance;
	
	@Override
	public void execute(Token precedingToken) {
		String typeName = precedingToken.lexeme;
		if(typeName.equals("int") || typeName.equals("float")){
			context.storedType = new PrimitiveType(precedingToken.lexeme);
		}else{
			SymbolTableEntry entry = context.currentSymbolTable.find(typeName);
			if(entry instanceof ClassEntry){
				context.storedType = new ClassType((ClassEntry) entry);
			}else{
				Log.error.println("ERROR: unknown type " + typeName + " at line " + precedingToken.lineno);
			}
		}
	}

}
