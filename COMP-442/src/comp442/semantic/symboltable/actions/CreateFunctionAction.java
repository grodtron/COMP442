package comp442.semantic.symboltable.actions;

import comp442.codegen.SpecialValues;
import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionContext;
import comp442.semantic.symboltable.SymbolAction;
import comp442.semantic.symboltable.entries.VariableEntry;
import comp442.semantic.symboltable.entries.types.PrimitiveType;

public class CreateFunctionAction extends SymbolAction {
	
	@Override
	public void execute(Token precedingToken) throws CompilerError {
		if(context.storedFunction != null){
			context.currentSymbolTable.add(context.storedFunction);
			context.currentSymbolTable = context.storedFunction.getScope();
			
			ExpressionContext.setCurrentFunction(context.storedFunction);
			
			// Adding these pseudo-parameters ensures that all stack-frame offsets will make sense.
			//
			// '@' sign guarantees no name collisions
			VariableEntry returnPcAddr    = new VariableEntry(SpecialValues.RETURN_ADDRESS_PARAMETER_NAME, new PrimitiveType("int"));

			//table.add(returnValueAddr);
			context.currentSymbolTable.add(returnPcAddr);

			ExpressionContext.setCurrentFunction(context.storedFunction);
			
			System.out.println("Adding function " + context.storedFunction);
		}
	}
}
