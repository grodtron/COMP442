package comp442.semantic.expressions;

import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;

public abstract class ExpressionElement {

	protected final ExpressionContext context = ExpressionContext.instance;
	
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		throw new InternalCompilerError("Unexpected " + e.getClass().getSimpleName() + " inside " + this.getClass().getSimpleName());
	}
	
}
