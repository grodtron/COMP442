package comp442.semantic.action;

import comp442.semantic.SymbolTable;
import comp442.semantic.symboltable.entries.FunctionEntry;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public final class SemanticContext {

	/*package*/final static SemanticContext instance;
	
	static {
		instance = new SemanticContext();
	}
	
	public SymbolTable currentSymbolTable;
	public SymbolTableEntryType storedType;
	public String storedId;
	public FunctionEntry storedFunction;
	public boolean skipNextCloseScope;
	
	private SemanticContext(){
		init();
	}

	public void init() {
		currentSymbolTable = new SymbolTable(null);
		storedType = null;
		storedId = null;
		skipNextCloseScope = false;
	}
	
	public static void reset(){
		instance.init();
	}

	public static String printableString() {
		return instance.currentSymbolTable.toString();
	}

	public static SymbolTableEntry find(String name) {
		return instance.currentSymbolTable.find(name);
	}

	public static SymbolTable getGlobalScope() {
		return instance.currentSymbolTable;
	}
	

	
	
}
