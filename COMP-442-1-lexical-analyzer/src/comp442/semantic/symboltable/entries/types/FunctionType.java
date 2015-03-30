package comp442.semantic.symboltable.entries.types;

import java.util.Collections;
import java.util.List;

public class FunctionType implements SymbolTableEntryType {

	private SymbolTableEntryType returnType;
	private List<SymbolTableEntryType> argumentTypes;
	
	public FunctionType(SymbolTableEntryType returnType, List<SymbolTableEntryType> argumentTypes){
		this.returnType = returnType;
		this.argumentTypes = argumentTypes;
	}

	public SymbolTableEntryType getReturnType() {
		return returnType;
	}

	public List<SymbolTableEntryType> getArgumentTypes() {
		return Collections.unmodifiableList(argumentTypes);
	}

	public void pushParameter(SymbolTableEntryType param) {
		argumentTypes.add(param);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		
		if(argumentTypes.size() > 0){
			sb.append(argumentTypes.get(0));
		}
		for(int i = 1; i < argumentTypes.size(); ++i){
			sb.append(", ");
			sb.append(argumentTypes.get(i));
		}
		sb.append(") -> ");
		sb.append(returnType);
		
		return sb.toString();
	}
	
}
