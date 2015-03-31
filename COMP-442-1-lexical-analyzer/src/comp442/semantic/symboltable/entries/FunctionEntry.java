package comp442.semantic.symboltable.entries;

import java.util.ArrayList;

import comp442.semantic.SymbolTable;
import comp442.semantic.symboltable.entries.types.FunctionType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public class FunctionEntry extends SymbolTableEntry {
	
	public FunctionEntry(String name, SymbolTableEntryType returnType, SymbolTable table) {
		super(name, Kind.Function, new FunctionType(returnType, new ArrayList<SymbolTableEntryType>()), table);
	}

	public void addParameter(ParameterEntry param) {
		FunctionType type = (FunctionType)getType();
		type.pushParameter(param.getType());
		
		getScope().add(param);
	}	
}
