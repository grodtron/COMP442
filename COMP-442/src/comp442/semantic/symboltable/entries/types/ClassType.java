package comp442.semantic.symboltable.entries.types;

import comp442.semantic.SymbolTable;
import comp442.semantic.symboltable.entries.ClassEntry;

public class ClassType implements SymbolTableEntryType {

	private ClassEntry classEntry;
	
	public ClassType(ClassEntry classEntry){
		this.classEntry = classEntry;
	}
	
	@Override
	public int getSize() {
		return classEntry.getSize();
	}
	
	@Override
	public String toString(){
		return classEntry.getName();
	}
	
	public SymbolTable getScope(){
		return classEntry.getScope();
	}
	
	@Override
	public boolean equals(Object other){
		return other instanceof ClassType && ((ClassType)other).classEntry.getName().equals(classEntry.getName());
	}

}
