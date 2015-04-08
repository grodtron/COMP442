package comp442.semantic.symboltable.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.symboltable.SymbolAction;
import comp442.semantic.symboltable.entries.ClassEntry;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.types.ClassType;
import comp442.semantic.symboltable.entries.types.PrimitiveType;

public class StoreTypeAction extends SymbolAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		String typeName = precedingToken.lexeme;
		if(typeName.equals("int") || typeName.equals("float")){
			context.storedType = new PrimitiveType(precedingToken.lexeme);
		}else{
			SymbolTableEntry entry = context.currentSymbolTable.find(typeName);
			if(entry instanceof ClassEntry){
				context.storedType = new ClassType((ClassEntry) entry);
			}else{
				throw new CompilerError("unknown type " + typeName);
			}
		}
	}

}
