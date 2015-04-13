package comp442.semantic.expressions;

import java.util.ArrayList;
import java.util.List;

import comp442.error.CompilerError;
import comp442.semantic.symboltable.SymbolContext;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.FunctionEntry;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.value.FunctionCallValue;
import comp442.semantic.value.Value;

public class FunctionCallExpressionFragment extends ExpressionElement {

	String id;
	
	List<AdditionExpressionFragment> expressions;
	
	SymbolTable surroundingScope;
	
	public FunctionCallExpressionFragment(String id){
		this.id = id;
		this.expressions = new ArrayList<AdditionExpressionFragment>();
		this.surroundingScope = SymbolContext.getCurrentScope();
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		
		if(e instanceof AdditionExpressionFragment){
			expressions.add((AdditionExpressionFragment) e);
		}else{
			super.acceptSubElement(e);			
		}		
	}
	
	@Override
	public Value getValue() throws CompilerError {
		SymbolTableEntry entry = surroundingScope.find(id);
		
		if(entry instanceof FunctionEntry){
			return new FunctionCallValue((FunctionEntry) entry, expressions);
		}else{
			throw new CompilerError("Could not find function " + id);
		}
	}

}