package comp442.semantic.symboltable.actions;

import comp442.lexical.token.Token;
import comp442.semantic.symboltable.SymbolAction;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.FunctionEntry;
import comp442.semantic.symboltable.entries.types.NoneType;

public class CreateProgramAction extends SymbolAction {

	@Override
	public void execute(Token precedingToken) {
		
		FunctionEntry program = new FunctionEntry("program", new NoneType(), new SymbolTable(context.currentSymbolTable));
		
		context.currentSymbolTable.add(program);
		
		context.currentSymbolTable = program.getScope();
		
	}

}
