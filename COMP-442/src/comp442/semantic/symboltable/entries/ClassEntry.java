package comp442.semantic.symboltable.entries;

import comp442.error.CompilerError;
import comp442.semantic.symboltable.SymbolTable;

public class ClassEntry extends SymbolTableEntry {

	public ClassEntry(String name, SymbolTable scope) {
		super(name, Kind.Class, null, scope);
	}

	@Override
	protected int calculateSize() throws CompilerError {
		int size = 0;
		for(SymbolTableEntry e : getScope().getEntries()){
			if(e instanceof VariableEntry){
				size += e.getSize();
			}
		}
		return size;
	}

}
