package comp442.semantic.symboltable.entries;

import comp442.semantic.SymbolTable;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public abstract class SymbolTableEntry {

	public static enum Kind {
		Function,
		Class,
		Parameter,
		Variable,
	};
	
	private String name;
	private Kind kind;
	private SymbolTableEntryType type;
	private SymbolTable scope;
	
	private int offset;
	
	public SymbolTableEntry(String name, Kind kind, SymbolTableEntryType type, SymbolTable scope){
		this.name  = name;
		this.kind  = kind;
		this.type  = type;
		this.scope = scope;
		
		this.offset = -1;
	}

	/**
	 * Force this {@link SymbolTableEntry} to calcuate its size.
	 * 
	 * For a variable or parameter this is the amount of space taken to store that
	 * value.
	 * 
	 * For a Class this is the amount of space required to store an instance of that class
	 * 
	 * For a function, this is the amount of space required for the function's stack frame
	 * 
	 * @return the calculated size as described above in units of bytes.
	 */
	protected abstract int calculateSize();
	
	public final int getSize(){
		return calculateSize(); // TODO - make lazy in calculating size
	}
	
	
	public String getName() {
		return name;
	}

	public Kind getKind() {
		return kind;
	}

	public SymbolTableEntryType getType() {
		return type;
	}

	public SymbolTable getScope() {
		return scope;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public void setType(SymbolTableEntryType type) {
		this.type = type;
	}

	public void setScope(SymbolTable scope) {
		this.scope = scope;
	}
	
	@Override
	public String toString(){
		return getKind() + " " + getName() + " " + getType(); 
	}
	
	@Override
	public boolean equals(Object other){
		if(other instanceof SymbolTableEntry){
			SymbolTableEntry e = (SymbolTableEntry) other;
			return ((e.getKind() == null  && getKind()  == null) || e.getKind().equals(getKind()))
				&& ((e.getType() == null  && getType()  == null) || e.getType().equals(getType()))
				&& ((e.getName() == null  && getName()  == null) || e.getName().equals(getName()))
				/*&& ((e.getScope() == null && getScope() == null) || e.getScope().equals(getScope()))*/;
		}else{
			return false;
		}
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getOffset(){
		return offset;
	}
}
