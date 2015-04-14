package comp442.semantic.expressions;

import java.util.List;

import comp442.codegen.MathOperation;
import comp442.codegen.Register;
import comp442.codegen.SpecialValues;
import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.semantic.symboltable.SymbolContext;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.MemberFunctionEntry;
import comp442.semantic.symboltable.entries.ParameterEntry;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.VariableEntry;
import comp442.semantic.symboltable.entries.types.ArrayType;
import comp442.semantic.symboltable.entries.types.ClassType;
import comp442.semantic.symboltable.entries.types.FunctionType;
import comp442.semantic.symboltable.entries.types.PrimitiveType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;
import comp442.semantic.value.DynamicValue;
import comp442.semantic.value.FunctionCallValue;
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
	
	private Value memberFunctionCallValue;
	
	SymbolTableEntryType currentType;
	
	private boolean isReference;
	
	public VariableExpressionFragment(String id) throws CompilerError{
		this.currentScope  = SymbolContext.getCurrentScope();
		this.enclosingScope = currentScope;
		// Stack pointer is always at the top of the stack frame, offsets are
		// stored as offset from the bottom of the frame.
		
		this.currentType = null;
		
		final SymbolTableEntry e = getEntry(id);

				
		// if we are in a member function
		if( enclosingScope.getEnclosingEntry() instanceof MemberFunctionEntry ){
			// get the class scope
			SymbolTable outerScope = enclosingScope.getParent();

			// and if the name we're looking for is not a local to the function
			// and is a class member ...
			if (outerScope.exists(id) && ! enclosingScope.exists(id)){
				init(getEntry(SpecialValues.THIS_POINTER_NAME));
				pushIdentifier(id);
			}else{
				init(e);
			}
		}else{
			init(e);
		}
		
	}

	private void init(final SymbolTableEntry e) throws CompilerError{
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
		}else
		if(e instanceof FunctionCallExpressionFragment){
			FunctionCallExpressionFragment f = (FunctionCallExpressionFragment) e;
			// then this is a member function call
			if(currentType instanceof ClassType){
				ClassType currentClass = (ClassType) currentType;
				
				// We don't want to do a recursive search for it, but rather check that it exists in the current scope
				if(currentScope.exists(f.getId())){
					SymbolTableEntry entry = currentScope.find(f.getId());
					if(entry instanceof MemberFunctionEntry){
						MemberFunctionEntry function = (MemberFunctionEntry) entry;
						
						
						List<TypedExpressionElement> expressions = f.getExpressions();
						
						expressions.add(0, this);
						
						// NB when created, FunctionCallValue converts all of its arguments to values.
						// as long as we do not update our type and stuff till *after* this then we should be safe (Y)
						memberFunctionCallValue = new FunctionCallValue(function, expressions);

						currentType = function.getType();
						context.finishTopElement();
					}else{
						throw new CompilerError("Cannot call non-function member " + f.getId() + " of class " + currentClass);
					}
				}else{
					throw new CompilerError("Cannot find method " + f.getId() + " of class " + currentClass);
				}
			}else{
				throw new CompilerError("Cannot call method " + f.getId() + " of non-class type " + currentType);
			}
		}else{
			super.acceptSubElement(e);
		}
	}

	@Override
	public Value getValue() {
		
		if(getType() instanceof PrimitiveType){
			return new StoredValue(baseAddr, offset);
		}if(getType() instanceof FunctionType){
			return memberFunctionCallValue;
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
