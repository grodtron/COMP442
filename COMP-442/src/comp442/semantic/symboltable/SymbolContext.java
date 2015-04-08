package comp442.semantic.symboltable;

import comp442.semantic.expressions.VariableBuilder;
import comp442.semantic.symboltable.entries.FunctionEntry;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public final class SymbolContext {

	/*package*/final static SymbolContext instance;
	
	static {
		instance = new SymbolContext();
	}
	
	public SymbolTable currentSymbolTable;
	public SymbolTableEntryType storedType;
	public String storedId;
	public FunctionEntry storedFunction;
	public boolean skipNextCloseScope;
	public VariableBuilder variableBuilder;
	
	private SymbolContext(){
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

	public static SymbolTable getCurrentScope() {
		return instance.currentSymbolTable;
	}
	

	
	
}
