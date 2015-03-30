package comp442.semantic.symboltable.entries;

import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public class ParameterEntry extends SymbolTableEntry {

	public ParameterEntry(String name, SymbolTableEntryType type) {
		super(name, Kind.Parameter, type, null);
	}
	
	@Override
	public String toString(){
		return getName() + " " + getKind() + " " + getType(); 
	}

}
