package comp442.semantic.expressions;

import comp442.error.CompilerError;
import comp442.semantic.SymbolTable;
import comp442.semantic.action.SemanticContext;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.types.ArrayType;
import comp442.semantic.symboltable.entries.types.ClassType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public class VariableBuilder {
	
	private SymbolTable scope;

	private int offset; // TODO convert to some "value" type
	
	SymbolTableEntry current;
	
	private ArrayIndexBuilder arrayIndexBuilder;
	
	public VariableBuilder(String id) throws CompilerError{
		this.scope  = SemanticContext.getCurrentScope();
		this.offset = 0;
		this.current = null;
		this.arrayIndexBuilder = new ArrayIndexBuilder();
		
		pushId(id);
	}
	
	public void pushId(String id) throws CompilerError{
		
		if(scope == null){
			throw new CompilerError("Cannot access property " + id + " of non-class type " + current.getType());
		}
		
		SymbolTableEntry e = scope.find(id);
		
		if(e == null){
			throw new CompilerError("Id " + id + " not found in current scope");
		}else{
			offset += arrayIndexBuilder.getOffset();
			
			offset += e.getOffset();
			current = e;
			
			SymbolTableEntryType type = e.getType();

			scope = null;
			
			if(type instanceof ClassType){
				scope = ((ClassType) type).getScope();
			}else
			if(type instanceof ArrayType){
				arrayIndexBuilder = new ArrayIndexBuilder((ArrayType)e.getType());
				if(((ArrayType) type).getType() instanceof ClassType){
					scope = ((ClassType) ((ArrayType) type).getType()).getScope(); // wow, ugly
				}
			}else{
				arrayIndexBuilder = new ArrayIndexBuilder(); // no-op arrayindexbuilder
			}
		}
	}
	
	public void pushIndex(int index) throws CompilerError{
		if( arrayIndexBuilder == null ){
			throw new CompilerError("Cannot index non-array type " + current); // TODO
		}else{
			arrayIndexBuilder.pushIndex(index);
		}
	}
	
	
	public int get(){
		return offset;
	}
}
