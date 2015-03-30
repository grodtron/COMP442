package comp442.semantic;

import java.util.HashMap;
import java.util.Map;

import comp442.semantic.symboltable.entries.SymbolTableEntry;

public class SymbolTable {

	private Map<String, SymbolTableEntry> entries;
	private final SymbolTable parent;
	
	public SymbolTable(SymbolTable parent){
		this.parent = parent;
		this.entries = new HashMap<String, SymbolTableEntry>();
	}

	public SymbolTableEntry find(String name) {
		return entries.get(name);
	}

	public void add(SymbolTableEntry entry) {
		entries.put(entry.getName(), entry);
	}

	public SymbolTable getParent() {
		return parent;
	}
	
}
