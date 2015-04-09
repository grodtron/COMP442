package comp442.semantic.expressions;

import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.semantic.value.Value;
import comp442.semantic.value.VoidValue;

public abstract class ExpressionElement {

	protected final ExpressionContext context = ExpressionContext.instance;
	
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		throw new InternalCompilerError("Unexpected " + e.getClass().getSimpleName() + " inside " + this.getClass().getSimpleName());
	}

	public void pushIdentifier(String id) throws CompilerError {
		throw new InternalCompilerError("Unexpected identifier " + id + " inside " + this.getClass().getSimpleName());
	}

	public void pushIndex(String index) throws CompilerError {
		throw new InternalCompilerError("Unexpected index " + index + " inside " + this.getClass().getSimpleName());
	}
	
	public Value getValue(){
		return VoidValue.get();
	}
	
}
