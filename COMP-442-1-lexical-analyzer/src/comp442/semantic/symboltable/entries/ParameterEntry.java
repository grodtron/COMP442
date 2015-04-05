package comp442.semantic.symboltable.entries;

import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public class ParameterEntry extends SymbolTableEntry {

	public ParameterEntry(String name, SymbolTableEntryType type) {
		super(name, Kind.Parameter, type, null);
	}

	@Override
	protected int calculateSize() {
		// Parameters are stored by value for primitives and by reference for composite types
		return 4;
	}
	
	
	
}
