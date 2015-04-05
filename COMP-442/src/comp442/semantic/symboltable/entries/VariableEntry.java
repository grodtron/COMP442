package comp442.semantic.symboltable.entries;

import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public class VariableEntry extends SymbolTableEntry {

	public VariableEntry(String name, SymbolTableEntryType type) {
		super(name, Kind.Variable, type, null);
	}

	@Override
	protected int calculateSize() {
		return getType().getSize();
	}
	
	
}
