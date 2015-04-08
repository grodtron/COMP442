package comp442.semantic.symboltable.entries.types;

import comp442.semantic.symboltable.SymbolTable;

public class NoneType implements SymbolTableEntryType {

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public SymbolTable getScope() {
		return null;
	}

}
