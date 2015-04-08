package comp442.semantic.expressions;

import comp442.error.CompilerError;
import comp442.semantic.symboltable.SymbolContext;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.types.ArrayType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public class VariableBuilder extends ExpressionElement {
	
	private SymbolTable scope;

	private int offset; // TODO convert to some "value" type
	
	SymbolTableEntryType currentType;
		
	public VariableBuilder(String id) throws CompilerError{
		this.scope  = SymbolContext.getCurrentScope();
		this.offset = 0;
		this.currentType = null;
		
		pushId(id);
	}
	
	public void pushId(String id) throws CompilerError{
		
		if(scope == null){
			throw new CompilerError("Cannot access property " + id + " of non-class type " + currentType);
		}
		
		SymbolTableEntry e = scope.find(id);
		
		if(e == null){
			throw new CompilerError("Id " + id + " not found in current scope");
		}else{
			offset      += e.getOffset();
			currentType  = e.getType();
			scope        = e.getScope();			
		}
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		if(e instanceof ArrayIndexBuilder){
			ArrayIndexBuilder a = (ArrayIndexBuilder) e;
			offset += a.getOffset();
			currentType = ((ArrayType) currentType).getType();
			scope = currentType.getScope();
		}else{
			super.acceptSubElement(e);
		}
	}
	
	public void pushIndex(int index) throws CompilerError{
		if(currentType instanceof ArrayType){
			ArrayIndexBuilder child = new ArrayIndexBuilder((ArrayType)currentType);
			child.pushIndex(index);
			context.pushChild(child);
		}else{
			throw new CompilerError("Cannot index non-array type " + currentType);
		}
	}
	
	
	public int get(){
		return offset;
	}
}
