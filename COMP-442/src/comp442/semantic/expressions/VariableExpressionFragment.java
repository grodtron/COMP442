package comp442.semantic.expressions;

import comp442.codegen.MathOperation;
import comp442.codegen.Register;
import comp442.error.CompilerError;
import comp442.semantic.symboltable.SymbolContext;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.types.ArrayType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;
import comp442.semantic.value.MathValue;
import comp442.semantic.value.RegisterValue;
import comp442.semantic.value.StaticIntValue;
import comp442.semantic.value.StoredValue;
import comp442.semantic.value.Value;

public class VariableExpressionFragment extends ExpressionElement {
	
	private SymbolTable scope;

	private Value offset;
	
	SymbolTableEntryType currentType;
		
	public VariableExpressionFragment(String id) throws CompilerError{
		this.scope  = SymbolContext.getCurrentScope();
		this.offset = new StaticIntValue(0);
		this.currentType = null;
		
		pushIdentifier(id);
	}
	
	@Override
	public void pushIdentifier(String id) throws CompilerError {
		
		if(scope == null){
			throw new CompilerError("Cannot access property " + id + " of non-class type " + currentType);
		}
		
		SymbolTableEntry e = scope.find(id);
		
		if(e == null){
			throw new CompilerError("Id " + id + " not found in current scope");
		}else{
			offset       = new MathValue(MathOperation.ADD, offset, new StaticIntValue(e.getOffset()));
			currentType  = e.getType();
			scope        = currentType.getScope();			
		}
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		if(e instanceof IndexingExpressionFragment){
			IndexingExpressionFragment a = (IndexingExpressionFragment) e;
			offset = new MathValue(MathOperation.ADD, offset, a.getValue());
			currentType = ((ArrayType) currentType).getType();
			scope = currentType.getScope();
		}else
		if(e instanceof AdditionExpressionFragment){
			if(currentType instanceof ArrayType){
				IndexingExpressionFragment child = new IndexingExpressionFragment((ArrayType)currentType);
				context.pushChild(child);
				child.acceptSubElement(e);
			}else{
				throw new CompilerError("Cannot index non-array type " + currentType);
			}
		}else{
			super.acceptSubElement(e);
		}
	}

	@Override
	public Value getValue() {
		return new StoredValue(new RegisterValue(Register.STACK_POINTER), offset);
	}
	
}
