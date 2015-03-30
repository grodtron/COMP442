package comp442.semantic.action;

import comp442.semantic.SymbolTable;
import comp442.semantic.symboltable.entries.FunctionEntry;
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
	
	private SemanticContext(){
		init();
	}

	public void init() {
		currentSymbolTable = new SymbolTable(null);
		storedType = null;
		storedId = null;
	}
	
	public static void reset(){
		instance.init();
	}

	public static String printableString() {
		return instance.currentSymbolTable.toString();
	}
	

	
	
}
