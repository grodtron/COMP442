package comp442.semantic.symboltable.entries;

import comp442.semantic.SymbolTable;

public class ClassEntry extends SymbolTableEntry {

	public ClassEntry(String name, SymbolTable scope) {
		super(name, Kind.Class, null, scope);
	}

}
