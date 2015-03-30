package comp442.semantic.symboltable.entries.types;

import java.util.Collections;
import java.util.List;

public class ArrayType implements SymbolTableEntryType {

	private final SymbolTableEntryType type;
	private final List<Integer> dimensions;
	
	public ArrayType(SymbolTableEntryType type, List<Integer> dimensions){
		this.type       = type;
		this.dimensions = dimensions;
	}

	public SymbolTableEntryType getType() {
		return type;
	}

	public List<Integer> getDimensions() {
		return Collections.unmodifiableList(dimensions);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(type);
		for(Integer i : dimensions){
			sb.append('[');
			sb.append(i);
			sb.append(']');
		}
		return sb.toString();
	}

	public void pushDimension(int dim) {
		dimensions.add(dim);
	}
	
}
