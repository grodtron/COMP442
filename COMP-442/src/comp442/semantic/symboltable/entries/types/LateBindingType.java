package comp442.semantic.symboltable.entries.types;

import comp442.error.CompilerError;
import comp442.semantic.symboltable.SymbolTable;

public abstract class LateBindingType implements SymbolTableEntryType {

	public abstract SymbolTableEntryType get() throws CompilerError;
	
	@Override
	public int getSize() throws CompilerError {
		return get().getSize();
	}

	@Override
	public SymbolTable getScope() throws CompilerError {
		return get().getScope();
	}

}
