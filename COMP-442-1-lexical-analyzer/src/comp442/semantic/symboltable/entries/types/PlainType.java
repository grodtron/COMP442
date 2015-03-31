package comp442.semantic.symboltable.entries.types;

public class PlainType implements SymbolTableEntryType{

	private final String name;
	
	// TODO - maybe need to distinguish between class type and primitives
	
	public PlainType(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public boolean equals(Object other){
		return other instanceof PlainType && name.equals(((PlainType)other).name);
	}
}
