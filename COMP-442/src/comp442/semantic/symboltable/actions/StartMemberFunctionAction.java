package comp442.semantic.symboltable.actions;

import comp442.codegen.SpecialValues;
import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.symboltable.SymbolAction;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.MemberFunctionEntry;
import comp442.semantic.symboltable.entries.ParameterEntry;

public class StartMemberFunctionAction extends SymbolAction {
	
	@Override
	public void execute(Token token) throws CompilerError {
		if(context.currentSymbolTable.exists(context.storedId)){
			context.storedFunction = null;
			context.skipNextCloseScope = true;
			
			throw new CompilerError("Duplicate function declaration: " + context.storedId);
		}else{
			SymbolTable table = new SymbolTable(context.currentSymbolTable);
			
			ParameterEntry thisParam = new ParameterEntry(SpecialValues.THIS_POINTER_NAME, context.currentSymbolTable.getEnclosingEntry().getType());
						
			context.storedFunction = new MemberFunctionEntry(context.storedId, context.storedType, table);
			context.storedFunction.addParameter(thisParam);
			
			table.setEnclosingEntry(context.storedFunction);
			
		}
	}

}
