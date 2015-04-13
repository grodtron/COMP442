package comp442.semantic.expressions;

import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public abstract class TypedExpressionElement extends ExpressionElement {

	public abstract SymbolTableEntryType getType();

}
