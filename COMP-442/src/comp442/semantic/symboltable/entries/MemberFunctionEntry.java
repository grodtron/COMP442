package comp442.semantic.symboltable.entries;

import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public class MemberFunctionEntry extends FunctionEntry {

	public MemberFunctionEntry(String name, SymbolTableEntryType returnType, SymbolTable table) {
		super(name, returnType, table);
	}

}
