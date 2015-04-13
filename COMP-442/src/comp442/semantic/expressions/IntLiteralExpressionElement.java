package comp442.semantic.expressions;

import comp442.semantic.symboltable.entries.types.PrimitiveType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;
import comp442.semantic.value.StaticIntValue;
import comp442.semantic.value.Value;

public class IntLiteralExpressionElement extends TypedExpressionElement {

	private final int i;
	
	public IntLiteralExpressionElement(int i){
		this.i = i;
	}
	
	@Override
	public Value getValue() {
		return new StaticIntValue(i);
	}

	@Override
	public SymbolTableEntryType getType() {
		return new PrimitiveType("int");
	}

}
