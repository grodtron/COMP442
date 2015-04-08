package comp442.semantic.symboltable.entries.types;

import comp442.semantic.symboltable.SymbolTable;

public interface SymbolTableEntryType {

	/**
	 * Get the amount of memory required to store a value of this type.
	 * 
	 * @return the size as described above in bytes
	 */
	int getSize();

	/**
	 * for a class type return that class type's scope, for all other types,
	 * returns `null`.
	 * @return The scope for this type
	 */
	SymbolTable getScope();

}
