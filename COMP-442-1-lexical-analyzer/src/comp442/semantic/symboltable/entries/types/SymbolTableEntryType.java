package comp442.semantic.symboltable.entries.types;

public interface SymbolTableEntryType {

	/**
	 * Get the amount of memory required to store a value of this type.
	 * 
	 * @return the size as described above in bytes
	 */
	int getSize();

}
