package comp442.semantic.expressions;

import comp442.codegen.MathOperation;
import comp442.codegen.Register;
import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.semantic.symboltable.SymbolContext;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.ParameterEntry;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.VariableEntry;
import comp442.semantic.symboltable.entries.types.ArrayType;
import comp442.semantic.symboltable.entries.types.PrimitiveType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;
import comp442.semantic.value.DynamicValue;
import comp442.semantic.value.IndirectValue;
import comp442.semantic.value.LateBindingDynamicValue;
import comp442.semantic.value.MathValue;
import comp442.semantic.value.RegisterValue;
import comp442.semantic.value.StaticIntValue;
import comp442.semantic.value.StoredValue;
import comp442.semantic.value.Value;

public class VariableExpressionFragment extends TypedExpressionElement {
	
	private final SymbolTable enclosingScope;
	private SymbolTable currentScope;

	private Value offset;
	private Value baseAddr;
	
	SymbolTableEntryType currentType;
	
	private boolean isReference;
	
	public VariableExpressionFragment(String id) throws CompilerError{
		this.currentScope  = SymbolContext.getCurrentScope();
		this.enclosingScope = currentScope;
		// Stack pointer is always at the top of the stack frame, offsets are
		// stored as offset from the bottom of the frame.
		
		this.currentType = null;
		
		final SymbolTableEntry e = getEntry(id);

		Value offsetValue = new  LateBindingDynamicValue() {			
			@Override
			public DynamicValue get() throws CompilerError {
				 return new MathValue(MathOperation.SUBTRACT, new StaticIntValue(e.getOffset()), new StaticIntValue(enclosingScope.getSize()));
			}
		};
		
		if(e instanceof VariableEntry || e.getType() instanceof PrimitiveType){
			isReference = false;
			baseAddr     = new RegisterValue(Register.STACK_POINTER);
			offset       = offsetValue;
		}else
		if(e instanceof ParameterEntry){
			isReference = true;
			baseAddr	= new StoredValue(new RegisterValue(Register.STACK_POINTER), offsetValue);
			offset      = new StaticIntValue(0);
		}else{
			throw new InternalCompilerError("Something went wrong, entry is not VariableEntry, type is not PrimitiveType *AND* it's not ParameterEntry");
		}
		
		currentType  = e.getType();
		currentScope = currentType.getScope();
		
	}
	
	private SymbolTableEntry getEntry(String id) throws CompilerError{
		if(currentScope == null){
			throw new CompilerError("Cannot access property " + id + " of non-class type " + currentType);
		}
		
		SymbolTableEntry e = currentScope.find(id);
		
		if(e == null){
			throw new CompilerError("Id " + id + " not found in current scope");
		}
		
		return e;
	}
	
	@Override
	public void pushIdentifier(String id) throws CompilerError {
		
		SymbolTableEntry e = getEntry(id);
	
		offset       = new MathValue(MathOperation.ADD, offset, new StaticIntValue(e.getOffset()));
		currentType  = e.getType();
		currentScope = currentType.getScope();
		
		isReference = false;
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		if(e instanceof IndexingExpressionFragment){
			IndexingExpressionFragment a = (IndexingExpressionFragment) e;
			offset = new MathValue(MathOperation.ADD, offset, a.getValue());
			currentType = ((ArrayType) currentType).getType();
			currentScope = currentType.getScope();
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
		
		if(getType() instanceof PrimitiveType){
			return new StoredValue(baseAddr, offset);
		}else{
			if(isReference){
				return baseAddr;
			}else{
				return new IndirectValue(new MathValue( MathOperation.ADD, baseAddr, offset));				
			}
		}
		
	}

	@Override
	public SymbolTableEntryType getType() {
		return currentType;
	}
	
}
